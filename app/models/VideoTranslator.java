package models;

import models.domain.Thumbnail;
import models.domain.VimeoTag;
import models.domain.VimeoVideo;

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

        for(Map<String, Object> v : rawVideos) {
            videos.add(createVideoObject(v));
        }

        return videos;
    }

    private static VimeoVideo createVideoObject(Map<String, Object> v) {
        VimeoVideo video = newVideoWithSimpleProperties(v);
        addTags(v, video);

        return video;
    }

    private static void addTags(Map<String, Object> v, VimeoVideo video) {
        List<Map<String, String>> tags = (List<Map<String, String>>) ((Map<String, Object>)v.get("tags")).get("tag");
        for(Map<String, String> tag : tags) {
            VimeoTag vTag = new VimeoTag(Integer.parseInt(tag.get("id")),
                                         tag.get("_content"),
                                         tag.get("url"));
            video.addTag(vTag);
        }
    }

    private static VimeoVideo newVideoWithSimpleProperties(Map<String, Object> v) {
        return new VimeoVideo(Integer.parseInt((String) v.get("id")),
                              (String) v.get("title"),
                              (String) v.get("description"),
                              Integer.parseInt((String) v.get("duration")),
                              createThumbnail(v));
    }

    private static Thumbnail createThumbnail(Map<String, Object> v) {
        Map<String, Object> tmp = (Map<String, Object>) v.get("thumbnails");
        List<Map<String, String>> map = (List<Map<String, String>>) tmp.get("thumbnail");

        for(Map<String, String> values : map) {
            if(values.get("width").equals("640")){
                return new Thumbnail(values.get("_content"),
                                     Integer.parseInt(values.get("width")),
                                     Integer.parseInt(values.get("height")));
            }

        }
        return Thumbnail.missing();

    }
}
