package models;

import play.Logger;

import javax.imageio.ImageIO;
import java.awt.color.CMMException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Knut Haugen <knuthaug@gmail.com>
 */
public class ImageFetcher {


    public static BufferedImage fetch(String url) {
        if(url == null || url.equals("")) {
            throw new IllegalArgumentException("invalid url given as argument to fetch(URL)");
        }

        try {
            Logger.info("Fetching image at url %s", url);
            URL newURL = new URL(url);
            return ImageIO.read(newURL);
        } catch (MalformedURLException e) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        } catch (CMMException colorException) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }
}
