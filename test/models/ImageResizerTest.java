package models;

import org.junit.Test;
import play.test.UnitTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Small class for resizing images.
 *
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageResizerTest extends UnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void willNotScaleImageThatDoesNotExist() {
        ImageResizer.resize(null);
    }

    @Test
    public void willScaleLargePortraitImageTo200WidthByDefault() throws Exception {
        BufferedImage original = ImageIO.read(new File("test/testdata/LargeImage.jpg"));
        BufferedImage resized = ImageResizer.resize(original);
        assertEquals(200, resized.getWidth());
        assertEquals(300, resized.getHeight());
    }

    @Test
    public void willNotScaleImageSmallerThan300TallByDefault() throws Exception {
        BufferedImage original = ImageIO.read(new File("test/testdata/smallImage.jpg"));

        BufferedImage resized = ImageResizer.resize(original);
        assertEquals(original.getWidth(), resized.getWidth());
    }

    @Test
    public void willScaleLandscapeTo300WideByDefault() throws Exception {
        BufferedImage resized = ImageResizer.resize(ImageIO.read(new File("test/testdata/landscape.jpg")));
        assertEquals(300, resized.getWidth());
        assertEquals(200, resized.getHeight());
    }

    @Test
    public void willScaleLandscapeTo100WideByArgument() throws Exception {
        BufferedImage resized = ImageResizer.resize(ImageIO.read(new File("test/testdata/landscape.jpg")), 100);
        assertEquals(100, resized.getWidth());
        assertEquals(67, resized.getHeight());
    }

    @Test
    public void willScaleLargePortraitImageTo100WidthByArgument() throws Exception {
        BufferedImage original = ImageIO.read(new File("test/testdata/LargeImage.jpg"));
        BufferedImage resized = ImageResizer.resize(original, 100);
        assertEquals(67, resized.getWidth());
        assertEquals(100, resized.getHeight());
    }

    @Test
    public void willNotScaleImageSmallerThan400TallByArgument() throws Exception {
        BufferedImage original = ImageIO.read(new File("test/testdata/smallImage.jpg"));

        BufferedImage resized = ImageResizer.resize(original, 400);
        assertEquals(original.getWidth(), resized.getWidth());
    }


}
