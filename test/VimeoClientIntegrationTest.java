import org.junit.Test;
import play.test.FunctionalTest;
import models.VimeoClient;

import static junit.framework.Assert.assertNotNull;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class VimeoClientIntegrationTest extends FunctionalTest {

    @Test
    public void clientCanFetchVimeoVideosSessions() {
        VimeoClient client = new VimeoClient();
        String result = client.doRequest("http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions");
        System.err.println(result);
        assertEquals(result, "");
    }
}