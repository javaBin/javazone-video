package controllers;

import com.google.common.base.Splitter;
import helpers.TagHelper;
import models.domain.Talk;
import play.Play;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

    public static void index() {
        List<Talk> talks = Talk.filter("year =", 2011).order("-plays").filter("type =", "jz").asList();
        List<String> tags = TagHelper.findTagsForTalks(talks);

        Iterable<String> speakerMenu = Splitter.on(",").split(Play.configuration.getProperty("speakers"));
        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        render(talks, tags, years, speakerMenu);
    }

}
