import com.google.common.base.Charsets;
import com.google.common.io.Files;
import fj.F;
import models.SessionJSONMapper;
import models.VideoInformationMerger;
import models.VideoJSONMapper;
import models.domain.IncogitoSession;
import models.domain.Talk;
import models.domain.VimeoVideo;
import org.junit.Test;
import play.test.FunctionalTest;

import java.io.File;
import java.util.List;

import static fj.data.List.iterableList;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */
public class MergeInformationIntegrationTest extends FunctionalTest {

    List<Talk> mergedTalks;

    @Test
    public void allVideosHaveTitles() throws Exception {
        fj.data.List<Talk> newList = iterableList(getVideos());

        assertEquals(true, newList.forall(new F<Talk, Boolean>() {
            @Override
            public Boolean f(Talk talk) {
                if(null != talk.title() && ! "".equals(talk.title())) {
                    return true;
                }
                return false;
            }
        }));
    }

    @Test
    public void allVideosHaveTags() throws Exception {
        fj.data.List<Talk> newList = iterableList(getVideos());

        assertEquals(true, newList.forall(new F<Talk, Boolean>() {
            @Override
            public Boolean f(Talk talk) {
                if(null != talk.tags() && talk.tags().size() >= 0) {
                    return true;
                }
                return false;
            }
        }));
    }


    private List<Talk> getVideos() throws Exception {
        if(mergedTalks != null) {
            return mergedTalks;
        }

        String videosString = readFile("test/testdata/videos.json");
        String sessionsString = readFile("test/testdata/sessions.json");
        VideoJSONMapper mapper = new VideoJSONMapper(videosString);

        List<VimeoVideo> videos = mapper.videosToObjects();
        List<IncogitoSession> sessions = new SessionJSONMapper(sessionsString).sessionsToObjects();
        mergedTalks = new VideoInformationMerger().mergeVideoAndSessionInfo(videos, sessions);
        return mergedTalks;
    }

    private String readFile(String fileName) throws Exception {
        return Files.toString(new File(fileName), Charsets.UTF_8);
    }
}