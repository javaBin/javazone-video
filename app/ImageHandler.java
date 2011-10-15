import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageHandler {

    private File dir;

    public ImageHandler(String basedir) {
        dir = new File(basedir);
    }

    public void handleImage(String basename, int year, String imageUrl) {
        File file = getFileName(basename, year);
        if(file.exists()) {
            return;
        }

        File previousFile = getFileName(basename, year - 1);
        if(previousFile.exists()) {
            previousFile.delete();
        }

        BufferedImage image =  ImageResizer.resize(ImageFetcher.fetch(imageUrl));
        try {
            ImageIO.write(image, "jpeg", file);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private File getFileName(String basename, int year) {
        return new File(dir + "/" + basename + "_" + year + ".jpg");
    }
}
