package models;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-02-26
 */
public class Mappings {
    private static final String KEY_TWITTER_NAME = "twitterName";
    private static final String KEY_BLOG = "blog";

    private FileInputStream file;
    public Map<String, Object> userMappings;


    public Mappings(String filename) {
        try {
            this.file = new FileInputStream(new File(filename));
            userMappings = (Map<String, Object>) new Yaml().load(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("File %s was not found (%s)", filename, e));
        }
    }

    public String twitterNameForUser(String userSlug) {
       return returnKey(userSlug, KEY_TWITTER_NAME);
    }

    public String blogForUser(String userSlug) {
        return returnKey(userSlug, KEY_BLOG);

    }

    private String returnKey(String userSlug, String key) {
        Map<String, Object> user = findUser(userSlug);
        if(user != null)     {
            return (String) findUser(userSlug).get(key);
        }

        return "";
    }

    private Map<String, Object> findUser(String userSlug) {
        return (Map<String, Object>) userMappings.get(userSlug);
    }
}
