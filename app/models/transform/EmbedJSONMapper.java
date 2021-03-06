package models.transform;

import models.domain.Embed;
import org.codehaus.jackson.map.ObjectMapper;
import play.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-07
 */
public class EmbedJSONMapper {
    private final ObjectMapper mapper;
    private final String json;

    public EmbedJSONMapper(final String jsonData) {
        mapper = new ObjectMapper();
        json = jsonData;
    }


    public Embed getEmbed() {
        try {
            Map<String, String> fields = mapper.readValue(json, Map.class);
            return new Embed(fields.get("html"));
        } catch (IOException e) {
            Logger.error(e.toString());
        }
        return new Embed("");
    }
}
