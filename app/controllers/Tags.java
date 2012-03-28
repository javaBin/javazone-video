package controllers;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import play.Play;
import play.mvc.Controller;

import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-03-28
 */
public class Tags extends Controller {
    public static void index() {
        Iterable <String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        List<String> tags = Lists.newArrayList("Java", "Scala", "JVM", "Design");
        render(tags, years);
    }

}
