import com.thebuzzmedia.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageResizer {

    private static final int TARGET_HEIGHT = 300;

    public static BufferedImage resize(BufferedImage image) {
        if(image == null) {
            throw new IllegalArgumentException("Invalid bufferedImage supplied");
        }

        if(image.getHeight() < TARGET_HEIGHT) {
            return image;
        }

        return Scalr.resize(image, Scalr.Method.QUALITY, TARGET_HEIGHT);
    }
}
