package controllers;

import models.domain.Video;
import play.mvc.Controller;

import java.util.List;

public class Application extends Controller {

    public static void index() {
        List<Video> videos = Video.findAll();
        render(videos);
    }

}