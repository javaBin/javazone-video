package controllers;

import com.google.common.base.Splitter;
import models.domain.Speaker;
import models.domain.Talk;
import play.Play;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-01
 */
public class Speakers extends Controller {

    public static void show(@Required String slug) {
        Speaker speaker = Speaker.find("bySlug", slug).first();

        if(speaker == null) {
            notFound("The speaker was not found. Sorry");
        }
        List<Talk> talks = Talk.filter("speakers elem", speaker).asList();
        //fetch other videos on video insert. Split between jz and non-jz
        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        render(speaker, talks, years);
    }

    public static void index() {
        List<Speaker> speakers = Speaker.all().asList();
        List<Talk> talkList = Talk.all().asList();
        HashMap talks = findTalksForSpeakers(talkList);
        Iterable <String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        render(speakers, talks, years);
    }

    private static HashMap findTalksForSpeakers(List<Talk> talkList) {
        HashMap talks = new LinkedHashMap<String, List<Talk>>();

        for(Talk talk : talkList) {
            addTalkToSpeakerList(talks, talk);
        }
        return talks;
    }

    private static void addTalkToSpeakerList(HashMap talks, Talk talk) {
        List<Speaker> talkSpeakers = talk.speakers();

        for(Speaker speaker : talkSpeakers) {
            checkForTalkAddition(talks, talk, speaker);
        }
    }

    private static void checkForTalkAddition(HashMap talks, Talk talk, Speaker speaker) {
        if(talks.containsKey(speaker.slug())) {
            List<Talk> speakerTalks = (List<Talk>) talks.get(speaker.slug());
            speakerTalks.add(talk);
        } else {
            List<Talk> addedTalks = new ArrayList<Talk>();
            addedTalks.add(talk);
            talks.put(speaker.slug(), addedTalks);
        }
    }
}
