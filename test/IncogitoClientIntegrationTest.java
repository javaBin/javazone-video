
import models.IncogitoClient;
import org.junit.Test;
import play.test.FunctionalTest;

import static junit.framework.Assert.assertNotNull;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class IncogitoClientIntegrationTest extends FunctionalTest {

    @Test
    public void clientCanFetchIncogitoSessions() {
        IncogitoClient client = new IncogitoClient();
        String result = client.doRequest("http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions");
        System.err.println(result);
        assertNotNull(result);
    }

}