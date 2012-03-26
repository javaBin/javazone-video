package controllers;

import com.google.common.base.Splitter;
import models.GuavaTools;
import models.domain.Talk;
import play.Play;
import play.mvc.Controller;

import java.util.List;

import static models.GuavaTools.collect;

public class Application extends Controller {

    static Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));

    public static void index() {
        List<Talk> talks = Talk.filter("year =", 2011).order("-plays").asList();
        Iterable<String> allTags = collect(talks, Talk.findTags());
        List<String> tags = GuavaTools.findMostPopularElements(allTags, 20);

        render(talks, tags, years);
    }

}
