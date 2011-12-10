package models;

import play.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageHandler {

    private File dir;

    public ImageHandler(String basedir) {
        dir = new File(basedir);
    }

    public void handleImage(String basename, int year, String imageUrl, Map<String, Integer> sizes) {
        Logger.info("Handling image files for basename=%s year=%s", basename, year);

        for(String sizeName : sizes.keySet()) {
            handleFile(basename, year, imageUrl, sizeName, sizes.get(sizeName));
        }

    }

    private void handleFile(String basename, int year, String imageUrl, String sizeName, int size) {
        File file = getFileName(basename, year, sizeName);
        Logger.info("Checking for size %s (wide=%s)", sizeName, size);

        if(file.exists()) {
            Logger.info("File exists. Skipping to next size");
            return;
        }

        File previousFile = getFileName(basename, year - 1, sizeName);

        if(previousFile.exists()) {
            Logger.info("File exists for basename, but for previous year. Updating it");
            previousFile.delete();
        }

        File nextFile = getFileName(basename, year + 1, sizeName);

        if(nextFile.exists()) {
            Logger.info("File exists for basename, but for more recent year year. Skipping this");
            return;
        }

        //must be a weak ref, or else we run out of memory quickly when fetching and scaling images
        WeakReference<BufferedImage> image =  new WeakReference<BufferedImage>(ImageResizer.resize(ImageFetcher.fetch(imageUrl), size));

        try {
            ImageIO.write(image.get(), "jpeg", file);
            System.gc();
        } catch (IOException e) {
            Logger.error("Error encountered when writing image %s to disk: %s", file, e);
        }
    }

    private File getFileName(String basename, int year, String size) {
        return new File(dir + "/" + basename + "_" + year + "_" + size + ".jpg");
    }
}
