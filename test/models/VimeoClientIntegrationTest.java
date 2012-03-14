package models;

import junit.framework.AssertionFailedError;
import models.domain.Embed;
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
    VimeoClient client;

    @Before
    public void setup() {
        client = new VimeoClient();
        args.put("per_page", "1");
    }

    @Test
    public void canFetchVimeoVideosByYear() {
        List<VimeoVideo> results = client.getVideosByYear("2011", args, 1);
        assertEquals(1, results.size());
    }

    @Test
    public void canFetchSeveralPages() {
        List<VimeoVideo> results = client.getVideosByYear("2011", args, 4);
        assertEquals(4, results.size());
    }

    @Test
    public void canFetchVideosFor2010() {
        List<VimeoVideo> results = client.getVideosByYear("2010", args, 1);
        assertEquals(1, results.size());

    }

    @Test
    public void fetchedVideosHaveEmbedCodes() {
        List<VimeoVideo> results = client.getVideosByYear("2010", args, 1);
        assertEquals(Embed.class, results.get(0).embedCode().getClass());

    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForIllegalYear() {
        List<VimeoVideo> results = client.getVideosByYear("2009", args, 1);
        assertEquals(1, results.size());

    }

    @Test
    public void handlesNullArgs() {
        List<VimeoVideo> results = client.getVideosByYear("2010", null, 1);
        assertEquals(50, results.size());
    }

    @Test
    public void handlesNullMax() {
        List<VimeoVideo> results = client.getVideosByYear("2010", null, null);
        assertEquals(65, results.size());
    }

    @Test
    @Ignore //slow
    public void clientCanFetchAll() {
        Map<String, String> args = new HashMap<String, String>();
        args.put("per_page", "50");

        List<VimeoVideo> results = client.getVideosByYear("2011", args, 0);
        assertEquals(130, results.size());
    }

    @Test
    public void clientCanFetchInformationForOneVideo() throws Exception {
        VimeoVideo video = client.getVideoById(28722593);
        assertEquals(28722593, video.id());
        assertLargerThan(75, video.plays());
    }

    private void assertLargerThan(int i, int plays) {
        if(!(i < plays)) {
            throw new AssertionFailedError(String.format("Expected %s to be larger than %s, but it wasn't", i, plays));
        }
    }


}