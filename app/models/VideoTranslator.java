package models;

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
        VimeoVideo video = new VimeoVideo();
        video.title((String) v.get("title"));
        video.id(Integer.parseInt((String) v.get("id")));
        video.description((String) v.get("description"));
        video.duration(Integer.parseInt((String) v.get("duration")));

        List<Map<String, String>> tags = (List<Map<String, String>>) ((Map<String, Object>)v.get("tags")).get("tag");
        for(Map<String, String> tag : tags) {
            VimeoTag vTag = new VimeoTag(Integer.parseInt(tag.get("id")),
                                         tag.get("_content"),
                                         tag.get("url"));
            video.addTag(vTag);
        }

        return video;
    }
}
