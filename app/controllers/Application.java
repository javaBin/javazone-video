package controllers;

import models.domain.Talk;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

    public static void index() {
        List<Talk> talks = Talk.filter("year =", 2011).order("-plays").asList();
        render(talks);
    }

    public static void filter(@Required String tag) {
        List<Talk> talks = Talk.filter("tags.name =", tag).order("-plays").asList();

        if(talks == null) {
            notFound("The talk was not found. Sorry");
        }

        renderTemplate("Application/index.html", talks);
    }

}