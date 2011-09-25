import com.google.common.base.Charsets;
import com.google.common.io.Files;
import fj.F;
import models.SessionJSONMapper;
import models.VideoInformationMerger;
import models.VideoJSONMapper;
import models.domain.IncogitoSession;
import models.domain.Video;
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

    List<Video> mergedVideos;

    @Test
    public void allVideosHaveTitles() throws Exception {
        fj.data.List<Video> newList = iterableList(getVideos());

        assertEquals(true, newList.forall(new F<Video, Boolean>() {
            @Override
            public Boolean f(Video video) {
                if(null != video.title() && ! "".equals(video.title())) {
                    return true;
                }
                return false;
            }
        }));
    }

    @Test
    public void allVideosHaveTags() throws Exception {
        fj.data.List<Video> newList = iterableList(getVideos());

        assertEquals(true, newList.forall(new F<Video, Boolean>() {
            @Override
            public Boolean f(Video video) {
                if(null != video.tags() && video.tags().size() >= 0) {
                    return true;
                }
                return false;
            }
        }));
    }


    private List<Video> getVideos() throws Exception {
        if(mergedVideos != null) {
            return mergedVideos;
        }

        String videosString = readFile("test/testdata/videos.json");
        String sessionsString = readFile("test/testdata/sessions.json");
        VideoJSONMapper mapper = new VideoJSONMapper(videosString);

        List<VimeoVideo> videos = mapper.videosToObjects();
        List<IncogitoSession> sessions = new SessionJSONMapper(sessionsString).sessionsToObjects();
        mergedVideos = new VideoInformationMerger().mergeVideoAndSessionInfo(videos, sessions);
        return mergedVideos;
    }

    private String readFile(String fileName) throws Exception {
        return Files.toString(new File(fileName), Charsets.UTF_8);
    }
}