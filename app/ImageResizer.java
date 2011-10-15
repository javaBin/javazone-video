import com.thebuzzmedia.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageResizer {

    private static final int TARGET_HEIGHT = 300;

    public static BufferedImage resize(String filename) {
        if(filename == null) {
            throw new IllegalArgumentException("Invalid filename supplied");
        }

        File image = new File(filename);
        if(image.exists()){
            BufferedImage raw = null;
            try {
                raw = ImageIO.read(image);
            } catch (IOException e) {
                throw new IllegalArgumentException("Image " + filename + " could not be read properly");
            }

            if(raw.getHeight() < TARGET_HEIGHT) {
                return raw;
            }

            return Scalr.resize(raw, Scalr.Method.QUALITY, TARGET_HEIGHT);
        }
        return new BufferedImage(1, 1, 1);
    }
}
