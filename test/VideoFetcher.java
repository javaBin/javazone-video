import models.IncogitoClient;
import models.VideoInformationMerger;
import models.VimeoClient;
import models.domain.Speaker;
import models.domain.Talk;
import models.domain.external.IncogitoSession;
import models.domain.external.VimeoVideo;
import org.junit.Ignore;
import org.junit.Test;
import play.test.FunctionalTest;

import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-24
 */
public class VideoFetcher extends FunctionalTest {

    @Test
    @Ignore
    public void fetchAndSaveVideos() {
        List<VimeoVideo> videos = new VimeoClient().getVideosByYear("2011", null, null);
        List<IncogitoSession> sessions = new IncogitoClient().getSessionsForYear(2011);

        List<Talk> finishedTalks = new VideoInformationMerger().mergeVideoAndSessionInfo(videos, sessions);

        videos = new VimeoClient().getVideosByYear("2010", null, null);
        sessions = new IncogitoClient().getSessionsForYear(2010);

        finishedTalks.addAll(new VideoInformationMerger().mergeVideoAndSessionInfo(videos, sessions));



        for(Talk talk : finishedTalks) {
            for(Speaker speaker : talk.speakers()) { //must save reference types first
                Speaker found = Speaker.find("bySlug", speaker.slug()).first();
                if(null != found) {
                    speaker = found;
                }

                speaker.save();
            }
            talk.save();
        }
    }

}
