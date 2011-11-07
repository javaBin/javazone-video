package models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageFetcher {


    public static BufferedImage fetch(String url) {
        if(url == null || url.equals("")) {
            throw new IllegalArgumentException("invalid url given as argument to fetch(URL)");
        }

        try {
            URL newURL = new URL(url);
            return ImageIO.read(newURL);
        } catch (MalformedURLException e) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }


    }
}
