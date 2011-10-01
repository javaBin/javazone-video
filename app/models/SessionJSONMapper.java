package models;

import models.domain.external.IncogitoSession;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-24
 */
public class SessionJSONMapper {

    private ObjectMapper mapper;
    private String json;

    public SessionJSONMapper(String sessionJson) {
        mapper = new ObjectMapper();
        json = sessionJson;
    }

    public List<IncogitoSession> sessionsToObjects() {
        if(null == json) {
            return new ArrayList<IncogitoSession>();
        }

        try {
            Map map = mapper.readValue(json, Map.class);
            return SessionTranslator.translateSessions((List<HashMap<String, Object>>) map.get("sessions"));
        } catch (IOException e) {
            e.printStackTrace(); //LOG?
        }

        return null;
    }
}
