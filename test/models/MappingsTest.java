package models;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-02-26
 */
public class MappingsTest {

    Mappings mappings;

    @Before
    public void setUp() {
        mappings = new Mappings("test/testdata/mappings.yml");
    }


    @Test(expected = IllegalArgumentException.class)
    public void illegal_filename_throws_illegalArgumentException() throws Exception {
        mappings = new Mappings("foo");
    }

    @Test
    public void find_twitter_name_for_slug() throws Exception {
        assertEquals("knuthaug", mappings.twitterNameForUser("knuthaugen"));
    }

    @Test
    public void invalid_name_returns_empty_twitter_name() throws Exception {
        assertEquals("", mappings.twitterNameForUser("foo"));
    }

    @Test
    public void find_blog_for_user_slug() throws Exception {
        assertEquals("http://blog.knuthaugen.no/", mappings.blogForUser("knuthaugen"));
    }

    @Test
    public void invalid_name_returns_empty_blog() throws Exception {
        assertEquals("", mappings.blogForUser("foo"));
    }

    @Test
    public void invalid_name_returns_empty_list_of_other_videos() {
        assertEquals(0, mappings.otherVideosForUser("foo").size());
    }

    @Test
    public void valid_name_returns_empty_list_of_other_videos() {
        Map<String, List<String>> videos = mappings.otherVideosForUser("kevlinhenney");
        assertEquals(2, videos.size());
        assertEquals(3, videos.get("øredev").size());
    }
}
