package controllers;

import models.domain.Talk;
import play.mvc.Controller;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-29
 */
public class Talks extends Controller {

    public static void show(int id) {
        Talk talk = Talk.find("byId", id).first();
        render(talk);
    }
}
