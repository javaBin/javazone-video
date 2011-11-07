import junit.framework.AssertionFailedError;
import models.ImageHandler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FunctionalTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
        new File("test/out/test_2011.jpg").delete();
    }

    @Test
    public void savesImageForNewAuthorToDisk() {
        ImageHandler handler = new ImageHandler("test/out");
        handler.handleImage("test", 2011,
                "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0");

        assertFileExist("test/out/test_2011.jpg");

    }

    @Test
    public void doesNotOverwriteImageForSameAuthorAndYear() throws Exception {
        File test = new File("test/out/test_2011.jpg");
        test.createNewFile();

        ImageHandler handler = new ImageHandler("test/out");
        handler.handleImage("test", 2011,
                "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0");

        assertFileExist("test/out/test_2011.jpg");
        assertEquals(null, ImageIO.read(test));
    }

     @Test
    public void updatesImagesForMoreRecentYear() throws Exception {
         File test = new File("test/out/test_2010.jpg");
         test.createNewFile();

         ImageHandler handler = new ImageHandler("test/out");

         handler.handleImage("test", 2011,
                 "http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/a21042d4-bb57-432a-bbfa-9766b90546d2/speaker-photos/0");

         assertFileExist("test/out/test_2011.jpg");
         assertFileDoesntExist("test/out/test_2010.jpg");
         BufferedImage in = ImageIO.read(new File("test/out/test_2011.jpg"));
         assertEquals(200, in.getWidth());  //scaled
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
