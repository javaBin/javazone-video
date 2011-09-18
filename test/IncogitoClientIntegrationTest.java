
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
    public void clientCanFetchIncogitoSessionsfor2011() {
        IncogitoClient client = new IncogitoClient();
        String result = client.getSessionForYear(2011);
        assertNotNull(result);
    }

    @Test
    public void clientCanFetchIncogitoSessionsfor2010() {
        IncogitoClient client = new IncogitoClient();
        String result = client.getSessionForYear(2010);
        assertNotNull(result);
    }

    @Test
    public void clientReturnsNullForInvalidYear() {
        IncogitoClient client = new IncogitoClient();
        String result = client.getSessionForYear(2000);
        assertNull(result);
    }



}