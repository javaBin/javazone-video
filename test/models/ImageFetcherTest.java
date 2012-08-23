package models;

import org.junit.Test;
import play.libs.F;
import play.test.FunctionalTest;

import java.awt.image.BufferedImage;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageFetcherTest extends FunctionalTest {

    @Test(expected = IllegalArgumentException.class)
    public void wontFetchNonvalidUrl() {
        ImageFetcher.fetch("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wontFetchNullUrl() {
        ImageFetcher.fetch(null);
    }

    public void wontFetchMalformedUrl() {
        F.Tuple<BufferedImage, String> image = ImageFetcher.fetch("http://:foo.com/url");
        assertEquals(image._1.getHeight(), 1);
    }

    @Test
    public void fetchesBufferedImageForValidUrl() {
        String testUrl = "http://javazone.no/incogito10/rest/events/JavaZone%202010/sessions/a18467d7-6eb2-4abd-97d1-b76b574c485d/speaker-photos/0";
        F.Tuple<BufferedImage, String> image = ImageFetcher.fetch(testUrl);
        assertEquals(489, image._1.getWidth());
        assertEquals("png", image._2);
    }

    @Test
    public void validButNonFunctionalUrlReturnsEmptyPicture() {
        String testUrl = "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a210424-bb57-432a-bbfa-9766b90546d2/speaker-photos/0";
        F.Tuple<BufferedImage, String> image = ImageFetcher.fetch(testUrl);
        assertEquals(1, image._1.getWidth());
        assertEquals("unknown", image._2);
    }


}
