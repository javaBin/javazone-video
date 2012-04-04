package controllers;

import com.google.common.base.Splitter;
import models.CollectionTools;
import models.domain.Talk;
import play.Play;
import play.libs.F;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-03-28
 */
public class Tags extends Controller {
    
    public static void index() {
        Iterable <String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));

        List<Talk> talks = Talk.all().asList();
        List<F.Tuple<String, Integer>> tags = CollectionTools.extractTagsWithCount(talks);

        Map<String, List<F.Tuple<String, Integer>>> ctags = splitTagsInCategories(tags);
        
        render(ctags, years);
    }

    private static Map<String, List<F.Tuple<String, Integer>>> splitTagsInCategories(List<F.Tuple<String, Integer>> tags) {
        Map<String, List<F.Tuple<String, Integer>>> categories = new HashMap<String, List<F.Tuple<String, Integer>>>();

        Map<String, String> map = mapTo("meta.tags", "Meta");
        map.putAll(mapTo("misc.tags", "Misc"));
        map.putAll(mapTo("methods.tags", "Methods"));
        map.putAll(mapTo("languages.tags", "Languages"));
        map.putAll(mapTo("frameworks.tags", "Frameworks"));
        map.putAll(mapTo("tech.tags", "Technology"));

        for(F.Tuple<String, Integer> tag : tags) {
            String match = map.get(tag._1);
            if(!categories.containsKey(match)){
                categories.put(match, new ArrayList<F.Tuple<String, Integer>>());
                categories.get(match).add(tag);
            } else {
                categories.get(match).add(tag);
            }
            
        }

        return categories;
    }
    
    private static Map <String, String> mapTo(String propertyName, String mapTarget) {
        Map<String, String> map = new HashMap<String, String>();
        Iterable <String> values = Splitter.on(",").split(Play.configuration.getProperty(propertyName));
        
        for(String key : values) {
            map.put(key, mapTarget);
        }
        
        return map;
    }

}
