package models;

import play.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-08-22
 */
public class ImageWriter {

    public ImageInfo write(BufferedImage image, File file, int year) {
        try {
            ImageIO.write(image, "jpeg", file);
            ImageInfo info = new ImageInfo(file.getPath(), image.getWidth(), image.getHeight(), year);
            return info;
        } catch (IOException e) {
            Logger.error("Error encountered when writing image %s to disk: %s", file, e);
        }
        return null;
    }


}
