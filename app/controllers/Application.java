package controllers;

import models.domain.Talk;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

    public static void index() {
        List<Talk> talks = Talk.filter("year =", 2011).order("-plays").asList();
        render(talks);
    }

}