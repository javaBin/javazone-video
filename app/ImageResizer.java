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

            if(raw.getHeight() < 300) {
                return raw;
            }

            return Scalr.resize(raw, Scalr.Method.QUALITY, 300);
        }
        return new BufferedImage(1, 1, 1);
    }
}
