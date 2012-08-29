package models.img;

import com.thebuzzmedia.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public final class ImageResizer {

    private static final int TARGET_HEIGHT = 300;

    public static BufferedImage resize(final BufferedImage image) {
        return resize(image, TARGET_HEIGHT);
    }

    public static BufferedImage resize(final BufferedImage image, final int targetHeight) {
        if(image == null) {
            throw new IllegalArgumentException("Invalid bufferedImage supplied");
        }

        if(image.getHeight() < targetHeight) {
            return image;
        }

        return Scalr.resize(image, Scalr.Method.QUALITY, targetHeight);
    }
}
