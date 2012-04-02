package controllers;

import com.google.common.base.Splitter;
import models.CollectionTools;
import models.domain.Talk;
import play.Play;
import play.libs.F;
import play.mvc.Controller;

import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-03-28
 */
public class Tags extends Controller {
    
    public static void index() {
        Iterable <String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));

        List<Talk> talks = Talk.all().asList();
        List<F.Tuple<String, Integer>> tags = CollectionTools.extractTagsWithCount(talks);
        
        render(tags, years);
    }

}
