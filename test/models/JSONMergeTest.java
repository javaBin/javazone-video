package models;

import models.domain.external.IncogitoSession;
import models.domain.Talk;
import models.domain.Thumbnail;
import models.domain.external.VimeoVideo;
import org.joda.time.DateTime;
import org.junit.Test;
import play.test.UnitTest;

import java.util.Arrays;
import java.util.List;

public class JSONMergeTest extends UnitTest {

    private VideoInformationMerger merger = new VideoInformationMerger();

    @Test
    public void mergeWithEmptyTitlesDoesNotMergeInfo() {
        List<IncogitoSession> sessions = Arrays.asList(new IncogitoSession("", "", 0));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(new VimeoVideo(0, "", "", 0, Thumbnail.missing(), 0, 0, 0, new DateTime(), ""));

        List<Talk> videos = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("", videos.get(0).talkAbstract());

    }

    @Test
    public void mergeWithSameTitlesDoesMergeInfo() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this is a test"));

        List<Talk> talks = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", talks.get(0).talkAbstract());
    }

    @Test
    public void mergeTwoWithSameTitlesDoesMergeInfo() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"),
                                                       createTestSession("this is a test2", "abstract2"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this is a test"),
                                                     createTestVideo("this is a test2"));

        List<Talk> talks = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", talks.get(0).talkAbstract());
        assertEquals("abstract2", talks.get(1).talkAbstract());
    }

    @Test
    public void videoInformationNotRetainedIfTitleDoesNotMatch() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this", "abstract"),
                                                       createTestSession("is", "abstract2"));
        List<VimeoVideo> vimeoVideos = Arrays.asList(createTestVideo("this is a test"),
                                                     createTestVideo("this is a test2"));

        List<Talk> talks = merger.mergeVideoAndSessionInfo(vimeoVideos, sessions);
        assertEquals(0, talks.size());
    }

    @Test
    public void mergeWhenTitleIsOneCharacterOff() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this is a tes"));

        List<Talk> talks = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", talks.get(0).talkAbstract());

    }

    @Test
    public void mergeWhenTitleIsTwoCharactersOff() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this s a tes"));

        List<Talk> talks = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", talks.get(0).talkAbstract());

    }


    @Test
    public void mergePlaysCommentsLikesFromVimeo() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(new VimeoVideo(0, "this is  test", "", 0, Thumbnail.missing(), 1, 2, 3, new DateTime(), ""));

        List<Talk> talks = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        Talk talk = talks.get(0);
        assertEquals("abstract", talk.talkAbstract());
        assertEquals(Integer.valueOf(1), talk.plays());
        assertEquals(Integer.valueOf(2), talk.comments());
        assertEquals(Integer.valueOf(3), talk.likes());



    }

    private VimeoVideo createTestVideo(String title) {
        VimeoVideo video = new VimeoVideo(0, title, "", 0, Thumbnail.missing(), 1, 1, 1, new DateTime(), "");
        return video;
    }



    private IncogitoSession createTestSession(String title, String anAbstract) {
        IncogitoSession session = new IncogitoSession(title, anAbstract, 2011);
        return session;
    }
}
