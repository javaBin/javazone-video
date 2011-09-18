package models;

import models.domain.IncogitoSession;
import models.domain.Video;
import models.domain.vimeo.VimeoVideo;
import org.junit.Test;
import play.test.UnitTest;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class JSONMergeTest extends UnitTest {

    private VideoInformationMerger merger = new VideoInformationMerger();

    @Test
    public void mergeWithEmptyTitlesDoesNotMergeInfo() {
        List<IncogitoSession> sessions = Arrays.asList(new IncogitoSession());
        List<VimeoVideo> vimeoVidoes = Arrays.asList(new VimeoVideo());

        List<Video> videos = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("", videos.get(0).talkAbstract());

    }

    @Test
    public void mergeWithSameTitlesDoesMergeInfo() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this is a test"));

        List<Video> videos = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", videos.get(0).talkAbstract());
    }

    @Test
    public void mergeTwoWithSameTitlesDoesMergeInfo() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"),
                                                       createTestSession("this is a test2", "abstract2"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this is a test"),
                                                     createTestVideo("this is a test2"));

        List<Video> videos = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", videos.get(0).talkAbstract());
        assertEquals("abstract2", videos.get(1).talkAbstract());
    }

    @Test
    public void videoInformationRetainedEvenIfTitleDoesNotMatch() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this", "abstract"),
                                                       createTestSession("is", "abstract2"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this is a test"),
                                                     createTestVideo("this is a test2"));

        List<Video> videos = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("this is a test", videos.get(0).title());
        assertEquals("this is a test2", videos.get(1).title());
    }

    @Test
    public void mergeWhenTitleIsOneCharacterOff() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this is a tes"));

        List<Video> videos = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", videos.get(0).talkAbstract());

    }

    @Test
    public void mergeWhenTitleIsTwoCharactersOff() {
        List<IncogitoSession> sessions = Arrays.asList(createTestSession("this is a test", "abstract"));
        List<VimeoVideo> vimeoVidoes = Arrays.asList(createTestVideo("this s a tes"));

        List<Video> videos = merger.mergeVideoAndSessionInfo(vimeoVidoes, sessions);
        assertEquals("abstract", videos.get(0).talkAbstract());

    }

    private VimeoVideo createTestVideo(String title) {
        VimeoVideo video = new VimeoVideo();
        video.title(title);
        return video;
    }

    private IncogitoSession createTestSession(String title, String anAbstract) {
        IncogitoSession session = new IncogitoSession();
        session.title(title);
        session.talkAbstract(anAbstract);
        return session;
    }
}
