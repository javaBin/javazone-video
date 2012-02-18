package models;

import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FunctionalTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-15
 */
public class ImageHandlerTest extends FunctionalTest {

    @BeforeClass
    public static void init() throws Exception {
        new File("test/out").mkdir();
    }

    @AfterClass
    public static void cleanup() {
        new File("test/out").delete();
    }

    @After
    public void tearDown() {
        new File("test/out/test_2011_large.jpg").delete();
        new File("test/out/test_2011_small.jpg").delete();
    }

    @Test
    public void savesImageForNewAuthorToDisk() {
        ImageHandler handler = new ImageHandler("test/out");
        handler.handleImage("test", 2011,
                "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0",
                new HashMap<String, Integer>(){{put("large", 300);}});

        assertFileExist("test/out/test_2011_large.jpg");

    }

    @Test
    public void doesNotOverwriteImageForSameAuthorAndYear() throws Exception {
        File test = new File("test/out/test_2011_large.jpg");
        test.createNewFile();

        ImageHandler handler = new ImageHandler("test/out");
        HashMap<String, ImageInfo> versions = handler.handleImage("test", 2011,
                "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0",
                new HashMap<String, Integer>(){{put("large", 300);}});

        assertFileExist("test/out/test_2011_large.jpg");
        assertEquals(null, ImageIO.read(test));
        
        assertEquals(0, versions.get("large").width());
    }

    @Test
    public void updatesImagesForMoreRecentYear() throws Exception {
        File test = new File("test/out/test_2010_large.jpg");
        test.createNewFile();

        ImageHandler handler = new ImageHandler("test/out");

        HashMap<String, ImageInfo> versions = handler.handleImage("test", 2011,
                 "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0",
                 new HashMap<String, Integer>(){{put("large", 300);}});

        assertFileExist("test/out/test_2011_large.jpg");
        assertFileDoesntExist("test/out/test_2010_large.jpg");
        BufferedImage in = ImageIO.read(new File("test/out/test_2011_large.jpg"));
        assertEquals(200, in.getWidth());  //scaled

        assertEquals(200, versions.get("large").width() );

    }


    @Test
    public void savesSmallImageVersionForNewAuthorToDisk() {
        ImageHandler handler = new ImageHandler("test/out");
        handler.handleImage("test", 2011,
                "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0",
                new HashMap<String, Integer>(){{put("large", 300); put("small", 100);}});

        assertFileExist("test/out/test_2011_small.jpg");
    }

    @Test
    public void smallImageIsScaledAccordingly() throws Exception {
        ImageHandler handler = new ImageHandler("test/out");
        handler.handleImage("test", 2011,
                "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0",
                new HashMap<String, Integer>(){{put("large", 300); put("small", 100);}});

        BufferedImage in = ImageIO.read(new File("test/out/test_2011_small.jpg"));
        assertEquals(100, in.getHeight());  //scaled
    }

    @Test
    public void returnsInfoAboutVersionsForImage() throws Exception {
        ImageHandler handler = new ImageHandler("test/out");
        HashMap<String, ImageInfo> versions = handler.handleImage("test", 2011,
                "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0",
                new HashMap<String, Integer>(){{put("large", 300); put("small", 100);}});

        assertEquals(300, versions.get("large").height());
        assertEquals(100, versions.get("small").height());
        assertEquals("test/out/test_2011_small.jpg", versions.get("small").url());
        assertEquals("test/out/test_2011_large.jpg", versions.get("large").url());
    }


    private void assertFileDoesntExist(String file) {
       File test = new File(file);
        if(test.exists()) {
            throw new AssertionFailedError("File " + test.getAbsolutePath() + " does indeed exist. It shouldn't");
        }
    }


    private void assertFileExist(String file) {
        File test = new File(file);
        if(!test.exists()) {
            throw new AssertionFailedError("File " + test.getAbsolutePath() + " does not exist");
        }
    }
}
