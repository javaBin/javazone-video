package models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

        for(String sizeName : sizes.keySet()) {
            handleFile(basename, year, imageUrl, sizeName, sizes.get(sizeName));
        }

    }

    private void handleFile(String basename, int year, String imageUrl, String sizeName, int size) {
        File file = getFileName(basename, year, sizeName);

        if(file.exists()) {
            return;
        }

        File previousFile = getFileName(basename, year - 1, sizeName);

        if(previousFile.exists()) {
            previousFile.delete();
        }

        BufferedImage image =  ImageResizer.resize(ImageFetcher.fetch(imageUrl), size);

        try {
            ImageIO.write(image, "jpeg", file);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private File getFileName(String basename, int year, String size) {
        return new File(dir + "/" + basename + "_" + year + "_" + size + ".jpg");
    }
}
