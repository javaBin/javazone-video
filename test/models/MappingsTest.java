package models;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-02-26
 */
public class MappingsTest {

    @Test
    public void finds_user_slug_in_mapping_file() throws Exception {
        Yaml parser = new Yaml();
        Map<String, Object> users = (LinkedHashMap<String, Object>) parser.load(new FileInputStream(new File("test/testdata/mappings.yml")));

        assertEquals(152, users.size());
    }

}
