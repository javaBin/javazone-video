
import models.IncogitoClient;
import models.domain.external.IncogitoSession;
import org.junit.Test;
import play.test.FunctionalTest;

import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class IncogitoClientIntegrationTest extends FunctionalTest {

    @Test
    public void clientCanFetchIncogitoSessionsfor2011() {
        IncogitoClient client = new IncogitoClient();
        List<IncogitoSession> result = client.getSessionsForYear(2011);
        assertEquals(130, result.size());
    }

    @Test
    public void clientCanFetchIncogitoSessionsfor2010() {
        IncogitoClient client = new IncogitoClient();
        List<IncogitoSession> result = client.getSessionsForYear(2010);
        assertEquals(151, result.size());
    }

    @Test
    public void clientReturnsEmptyListForInvalidYear() {
        IncogitoClient client = new IncogitoClient();
        List<IncogitoSession> result = client.getSessionsForYear(2000);
        assertEquals(0, result.size());
    }

}