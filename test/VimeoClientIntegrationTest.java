import models.VimeoClient;
import models.domain.external.VimeoVideo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import play.test.FunctionalTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class VimeoClientIntegrationTest extends FunctionalTest {

    private Map<String, String> args = new HashMap<String, String>();

    @Before
    public void setup() {
        args.put("per_page", "1");
    }

    @Test
    public void canFetchVimeoVideosByTag() {
        VimeoClient client = new VimeoClient();

        List<VimeoVideo> results = client.getVideosByYear("2011", args, 1);
        assertEquals(1, results.size());
    }

    @Test
    public void canFetchSeveralPages() {
        VimeoClient client = new VimeoClient();

        List<VimeoVideo> results = client.getVideosByYear("2011", args, 4);
        assertEquals(4, results.size());
    }

    @Test
    public void canFetchVideosFor2010() {
        VimeoClient client = new VimeoClient();

        List<VimeoVideo> results = client.getVideosByYear("2010", args, 1);
        assertEquals(1, results.size());

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForIllegalYear() {
        VimeoClient client = new VimeoClient();

        List<VimeoVideo> results = client.getVideosByYear("2009", args, 1);
        assertEquals(1, results.size());

    }

    @Test
    public void handlesNullArgs() {
        VimeoClient client = new VimeoClient();
        List<VimeoVideo> results = client.getVideosByYear("2010", null, 1);
        assertEquals(50, results.size());
    }

     @Test
    public void handlesNullMax() {
        VimeoClient client = new VimeoClient();
        List<VimeoVideo> results = client.getVideosByYear("2010", null, null);
        assertEquals(68, results.size());
    }

    @Test
    @Ignore //slow
    public void clientCanFetchAll() {
        VimeoClient client = new VimeoClient();
        Map<String, String> args = new HashMap<String, String>();
        args.put("per_page", "50");

        List<VimeoVideo> results = client.getVideosByYear("2011", args, 0);
        assertEquals(130, results.size());
    }


}