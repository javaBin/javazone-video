package models;

import models.domain.external.VimeoVideo;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */

public class VideoJSONMapper {
    private ObjectMapper mapper;
    private String json;

    public VideoJSONMapper(final String jsonData) {
        mapper = new ObjectMapper();
        json = jsonData;
    }

    public List<VimeoVideo> videosToObjects() {

        if(json == null) {
            return new ArrayList<VimeoVideo>();
        }

        try {
            Map map = mapper.readValue(json, Map.class);
            Map<String, Object> videos = (Map<String, Object>) map.get("videos");
            return VideoTranslator.translateVideos((List<HashMap<String, Object>>) videos.get("video"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Integer getTotalVideos() {
        try {
            Map map = mapper.readValue(json, Map.class);
            Map<String, Object> videos = (Map<String, Object>) map.get("videos");
            return Integer.parseInt((String) videos.get("total"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}