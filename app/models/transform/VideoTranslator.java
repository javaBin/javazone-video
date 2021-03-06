package models.transform;

import fj.F;
import fj.data.Array;
import models.domain.Thumbnail;
import models.domain.external.VimeoVideo;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class VideoTranslator {


    public static List<VimeoVideo> translateVideos(List<HashMap<String, Object>> rawVideos) {
        List<VimeoVideo> videos = new ArrayList<VimeoVideo>();

        for (Map<String, Object> v : rawVideos) {
                videos.add(createVideoObject(v));
        }

        return videos;
    }

    public static VimeoVideo translateVideo(HashMap<String, Object> rawVideo) {
        return createVideoObject(rawVideo);
    }

    private static VimeoVideo createVideoObject(Map<String, Object> v) {
        VimeoVideo video = newVideoWithSimpleProperties(v);
        addTags(v, video);

        return video;
    }

    private static void addTags(Map<String, Object> v, VimeoVideo video) {
        if(!v.containsKey("tags")) {
            return;
        }

        List<Map<String, String>> tags = (List<Map<String, String>>) ((Map<String, Object>) v.get("tags")).get("tag");

        final Array<String> filteredTags = Array.array("JavaZOne 2011", "JavaZone 2011","Conference",
                                    "JavaZone 2009", "JavaZone 2010", "JavaZone 2012", "javazone 20120",
                                    "Javazone 2012", "JavaZone", "JavaBin", "JavaZone2010");

        for (Map<String, String> tag : tags) {
            final String content = tag.get("_content");
            if(filteredTags.exists(new F<String, Boolean>() {
                @Override
                public Boolean f(String s) {
                    return s.equals(content);
                }
            })) {
                continue;
            }
            video.addTag(Integer.parseInt(tag.get("id")),
                    tag.get("_content"),
                    tag.get("url"));

        }
    }

    private static VimeoVideo newVideoWithSimpleProperties(Map<String, Object> v) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = formatter.parseDateTime((String) v.get("upload_date"));

        String duration = (String) v.get("duration");
        if(duration.contains(".")) {
            duration = duration.substring(duration.lastIndexOf(".") + 1);
        }
        return new VimeoVideo(Integer.parseInt((String) v.get("id")),
                (String) v.get("title"),
                (String) v.get("description"),
                Integer.parseInt(duration),
                createThumbnail(v),
                Integer.parseInt((String) v.get("number_of_plays")),
                Integer.parseInt((String) v.get("number_of_comments")),
                Integer.parseInt((String) v.get("number_of_likes")),
                dateTime,
                (String) v.get("privacy"));
    }

    private static Thumbnail createThumbnail(Map<String, Object> v) {
        Map<String, Object> tmp = (Map<String, Object>) v.get("thumbnails");
        List<Map<String, String>> map = (List<Map<String, String>>) tmp.get("thumbnail");

        for (Map<String, String> values : map) {
            if (values.get("width").equals("640")) {
                return new Thumbnail(values.get("_content"),
                        Integer.parseInt(values.get("width")),
                        Integer.parseInt(values.get("height")));
            }

        }
        return Thumbnail.missing();

    }
}
