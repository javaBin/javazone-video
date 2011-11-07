package models;

import org.junit.Test;
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
        BufferedImage image = ImageFetcher.fetch("http://:foo.com/url");
        assertEquals(image.getHeight(), 1);
    }

    @Test
    public void fetchesBufferedImageForValidUrl() {
        String testUrl = "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0";
        BufferedImage image = ImageFetcher.fetch(testUrl);
        assertEquals(534, image.getWidth());
    }

    @Test
    public void validButNonFunctionalUrlReturnsEmptyPicture() {
        String testUrl = "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a210424-bb57-432a-bbfa-9766b90546d2/speaker-photos/0";
        BufferedImage image = ImageFetcher.fetch(testUrl);
        assertEquals(1, image.getWidth());
    }


}
