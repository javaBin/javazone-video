package models;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import play.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.color.CMMException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Iterator;

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
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);

            InputStream in = client.execute(httpget).getEntity().getContent();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            org.apache.commons.io.IOUtils.copy(in, baos);
            byte[] bytes = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            BufferedImage img = ImageIO.read(bais);
            bais.reset();
            Logger.info("first=" + getFormatName(bais));
            return img;
        } catch (MalformedURLException e) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        } catch (CMMException colorException) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }

    private static String getFormatName(InputStream o) {
        try {
            // Create an image input stream on the image
            ImageInputStream iis = ImageIO.createImageInputStream(o);

            // Find all image readers that recognize the image format
            Iterator iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                // No readers found
                return null;
            }

            // Use the first reader
            ImageReader reader = (ImageReader)iter.next();

            // Close stream
            iis.close();

            // Return the format name
            return reader.getFormatName();
        } catch (IOException e) {
        }
        // The image could not be read
        return null;
    }
}
