package models;

import models.domain.vimeo.VimeoVideo;
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

    public List<VimeoVideo> videosToObjects(String videosString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, Object> map = mapper.readValue(videosString,  Map.class);
            Map<String, Object> videos = (Map<String, Object>) map.get("videos");
            return VideoTranslator.translateVideos((List<HashMap<String, Object>>) videos.get("video"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}