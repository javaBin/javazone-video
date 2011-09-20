import models.domain.vimeo.VimeoVideo;
import org.junit.Test;
import play.test.FunctionalTest;
import models.VimeoClient;

import java.util.HashMap;
import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class VimeoClientIntegrationTest extends FunctionalTest {

    @Test
    public void clientCanFetchVimeoVideos() {
        VimeoClient client = new VimeoClient();

        String result = client.getAllVideos(new HashMap<String, String>(){{ put("per_page", "1");}});
        assertNotNull(result);
    }


}