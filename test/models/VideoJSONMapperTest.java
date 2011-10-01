package models;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import models.domain.Thumbnail;
import models.domain.external.VimeoVideo;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */
public class VideoJSONMapperTest extends UnitTest {


    private static VimeoVideo video;;

    @Before
    public void init() throws IOException {
        video = getVideoElement();
    }


    @Test
    public void mapVideoJSONToListOfVideos() throws Exception {
        String videosJson = Files.toString(new File("test/testdata/twoVideos.json"), Charsets.UTF_8);
        VideoJSONMapper mapper = new VideoJSONMapper(videosJson);
        List<VimeoVideo> videos = mapper.videosToObjects();

        assertEquals(2, videos.size());
    }

    @Test
    public void wrapperForTitleInVideoElement() throws Exception {
        assertEquals("Deploying Apps on Heroku", video.title());
    }

    @Test
    public void isWrapperForId() {
        assertEquals(28803302, video.id());
    }

    @Test
    public void isWrapperForDescription() {
        assertLargerThan(10, video.description().length());
    }

    @Test
    public void isWrapperForDuration() {
        assertEquals(3755, video.duration());
    }

    @Test
    public void isWrapperForTags() {
        assertEquals(5, video.tags().size());
    }

    @Test
    public void tagsHaveId() {
        VimeoVideo.Tag tag = video.tags().get(0);
        assertEquals(64157810, tag.id());
    }

    @Test
    public void tagsHaveName() {
        VimeoVideo.Tag tag = video.tags().get(0);
        assertEquals("JavaZOne 2011", tag.name());
    }

    @Test
    public void tagsHaveUrl() {
        VimeoVideo.Tag tag = video.tags().get(0);
        assertEquals("http://vimeo.com/tag:javazone2011", tag.url());
    }

    @Test
    public void videosHaveThumbnails() {
        Thumbnail thumb = video.thumbnail();
        assertEquals(640, thumb.width());
        assertEquals(204, thumb.height());
        assertEquals("http://b.vimeocdn.com/ts/192/437/192437267_640.jpg", thumb.url());
    }

    @Test
    public void getTotalNumberFromMetadata() throws Exception {
        String videosJson = Files.toString(new File("test/testdata/twoVideos.json"), Charsets.UTF_8);
        VideoJSONMapper mapper = new VideoJSONMapper(videosJson);
        assertEquals(new Integer(190), mapper.getTotalVideos());
    }

    @Test
    public void returnsEmptyListForNullString() {
        VideoJSONMapper mapper = new VideoJSONMapper(null);
        assertEquals(0, mapper.videosToObjects().size());
    }

    private void assertLargerThan(int i, int length) {
        if(length < i) {
            throw new AssertionError("expected " + length + " > " + i + " but was " + length + " < " + i);
        }
    }

    private static VimeoVideo getVideoElement() throws IOException {
        String videosJson = Files.toString(new File("test/testdata/twoVideos.json"), Charsets.UTF_8);
        VideoJSONMapper mapper = new VideoJSONMapper(videosJson);
        return mapper.videosToObjects().get(0);
    }

}
