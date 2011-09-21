package models;

import models.domain.VimeoVideo;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */

public class JSONMapper {
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<VimeoVideo> videosToObjects(String videosString) {


        try {
            Map<String, Object> map = mapper.readValue(videosString,  Map.class);
            Map<String, Object> videos = (Map<String, Object>) map.get("videos");
            return VideoTranslator.translateVideos((List<HashMap<String, Object>>) videos.get("video"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Integer getTotalVideos(String videosJson) {
        try {
            Map<String, Object> map = mapper.readValue(videosJson,  Map.class);
            Map<String, Object> videos = (Map<String, Object>) map.get("videos");
            return Integer.parseInt((String) videos.get("total"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}