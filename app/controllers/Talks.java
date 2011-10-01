package controllers;

import models.domain.Talk;
import play.data.validation.Required;
import play.mvc.Controller;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-29
 */
public class Talks extends Controller {

    public static void show(@Required int id) {
        Talk talk = Talk.find("byId", id).first();

        if(talk == null) {
            notFound("The talk was not found. Sorry");
        }

        render(talk);
    }
}
