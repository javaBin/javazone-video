package controllers;

import com.google.common.base.Splitter;
import models.CollectionTools;
import models.domain.Talk;
import play.Play;
import play.mvc.Controller;

import java.util.List;

import static models.CollectionTools.collect;

public class Application extends Controller {

    public static void index() {
        List<Talk> talks = Talk.filter("year =", 2011).order("-plays").asList();
        Iterable<String> allTags = collect(talks, Talk.findTags());
        List<String> tags = CollectionTools.findMostPopularElements(allTags, 20);
        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));

        render(talks, tags, years);
    }

}
