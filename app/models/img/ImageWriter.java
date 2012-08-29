package models.img;

import play.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 */
public class ImageWriter {

    public ImageInfo write(BufferedImage image, String format, File file, int year) {
        try {
            Logger.debug("writing image format %s to file %s", format, file);
            ImageIO.write(image, format, new File(file.getAbsolutePath() ));
            ImageInfo info = new ImageInfo(file.getPath(), image.getWidth(), image.getHeight(), year);
            return info;
        } catch (IOException e) {
            Logger.error("Error encountered when writing image %s to disk: %s", file, e);
        }
        return null;
    }


}
