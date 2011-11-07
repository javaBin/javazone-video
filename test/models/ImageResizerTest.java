package models;

import models.ImageResizer;
import org.junit.Test;
import play.test.UnitTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageResizerTest extends UnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void willNotScaleImageThatDoesNotExist() {
        ImageResizer.resize(null);
    }

    @Test
    public void willScaleLargePortraitImageTo200Width() throws Exception {
        BufferedImage original = ImageIO.read(new File("test/testdata/LargeImage.jpg"));
        BufferedImage resized = ImageResizer.resize(original);
        assertEquals(200, resized.getWidth());
        assertEquals(300, resized.getHeight());
    }

    @Test
    public void willNotScaleImageSmallerThat300Tall() throws Exception {
        BufferedImage original = ImageIO.read(new File("test/testdata/smallImage.jpg"));

        BufferedImage resized = ImageResizer.resize(original);
        assertEquals(original.getWidth(), resized.getWidth());
    }

    @Test
    public void willScaleLandscapeTo300Wide() throws Exception {
        BufferedImage resized = ImageResizer.resize(ImageIO.read(new File("test/testdata/landscape.jpg")));
        assertEquals(300, resized.getWidth());
        assertEquals(200, resized.getHeight());
    }

}
