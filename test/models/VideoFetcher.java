package models;

import models.domain.Speaker;
import models.domain.Talk;
import models.domain.TalkTypes;
import models.domain.external.IncogitoSession;
import models.domain.external.VimeoVideo;
import org.joda.time.DateTime;
import org.junit.Test;
import play.test.FunctionalTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com> 2011-09-24
 *
 * This is a functional test written to easily fetch videos from video, session info from incogito and
 * merge them and store in the database. The test should be ignored when not running it since
 * running time is long.
 */
public class VideoFetcher extends FunctionalTest {

    @Test
    public void fetchAndSaveVideos() {
        List<VimeoVideo> videos = new VimeoClient().getVideosByYear("2011", null, null);
        List<IncogitoSession> sessions = new IncogitoClient().getSessionsForYear(2011);

        List<Talk> finishedTalks = new VideoInformationMerger().mergeVideoAndSessionInfo(videos, sessions);

        Mappings mappings = new Mappings("conf/mappings.yml");
        
        videos = new VimeoClient().getVideosByYear("2010", null, null);
        sessions = new IncogitoClient().getSessionsForYear(2010);

        finishedTalks.addAll(new VideoInformationMerger().mergeVideoAndSessionInfo(videos, sessions));

        for(Talk talk : finishedTalks) {
            for(Speaker speaker : talk.speakers()) { //must save reference types first
                Speaker found = Speaker.find("bySlug", speaker.slug()).first();

                if(null != found) {
                    speaker = found;
                }

                speaker.twitterName(mappings.twitterNameForUser(speaker.slug()));
                handleImages(talk, speaker);
                speaker.save();
            }

            talk.save();
        }

        List<Speaker> speakers = Speaker.all().asList();
        VimeoClient vimeo = new VimeoClient();

        for(Speaker speaker : speakers) {
            Map<String, List<String>> extraTalkUrls = mappings.otherVideosForUser(speaker.slug());

            for(String venue : extraTalkUrls.keySet()) {
                List<String> urls = extraTalkUrls.get(venue);
                for(String url : urls) {
                    Integer id = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1));
                    VimeoVideo videoInfo = vimeo.getVideoById(id);
                    Talk talk = new VideoInformationMerger().talkFromVideo(videoInfo);
                    talk.year(getYear(videoInfo.uploadDate()));
                    talk.addSpeaker(speaker);
                    talk.type(TalkTypes.OTHER);
                    talk.save();
                }
            }
        }

    }

    private Integer getYear(DateTime dateTime) {
        return dateTime.getYear();
    }

    private void handleImages(Talk talk, Speaker speaker) {
        HashMap<String, Integer> sizes = new HashMap<String, Integer>(){{
                put("large", 200);
                put("small", 80);
        }};

        ImageHandler handler = new ImageHandler("public/images/speakers");
        HashMap<String, ImageInfo> versions = handler.handleImage(speaker.slug(), talk.year(),
                                                               speaker.photoURL(), sizes);

        if((speaker.images() == null || speaker.images().isEmpty()) || isNewerVersions(versions, speaker.images()) ) {
            speaker.images(versions);
        }
    }

    private boolean isNewerVersions(HashMap<String, ImageInfo> newImages, HashMap<String, ImageInfo> oldImages) {
        return !newImages.isEmpty() && newImages.get("large").year() > oldImages.get("large").year();
    }

}
