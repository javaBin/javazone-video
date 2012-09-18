package controllers;

import helpers.ControllerHelper;
import helpers.TagHelper;
import models.domain.Talk;
import play.Play;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

    public static void index() {
        int defaultYear = Integer.parseInt(Play.configuration.getProperty("default.year", "2012"));
        List<Talk> talks = Talk.filter("year =", defaultYear).order("-plays").filter("type =", "jz").asList();
        List<String> tags = TagHelper.findTagsForTalks(talks);

        Iterable<String> speakerMenu = ControllerHelper.getSpeakersMenuValue();
        Iterable<String> years = ControllerHelper.getYearsMenuValue();
        render(talks, tags, years, speakerMenu);
    }

}
