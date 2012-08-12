package controllers;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.Query;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import models.domain.Speaker;
import models.domain.Talk;
import play.Play;
import play.data.validation.Required;
import play.modules.morphia.MorphiaPlugin;
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

    private static final List<String> alphabet = new ArrayList<String>() {{
        add("A");add("B");add("C");add("D");add("E");add("F");add("G");
        add("H");add("I");add("J");add("K");add("L");add("M");add("N");
        add("O");add("P");add("Q");add("R");add("S");add("T");add("U");
        add("V");add("W");add("X");add("Y");add("Z");add("Æ");add("Ø");add("Ö");add("Ä");add("Å");
    }};


    public static void show(@Required String slug) {
        Speaker speaker = Speaker.find("bySlug", slug).first();

        if(speaker == null) {
            notFound("The speaker was not found. Sorry");
        }

        List<Talk> talks = Talk.filter("speakers elem", speaker).filter("type = ", "jz").asList();
        List<Talk> otherTalks = Talk.filter("speakers elem", speaker).filter("type = ", "other").asList();

        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        Iterable<String> speakerMenu = Splitter.on(",").split(Play.configuration.getProperty("speakers"));
        render(speaker, talks, otherTalks, years, speakerMenu);
    }

    public static void index(@Required String letters) {
        Query<Speaker> q = createQuery(letters);

        List<Speaker> speakers = q.order("lastname").asList();
        List<Talk> talkList = Talk.all().asList();
        HashMap talks = findTalksForSpeakers(talkList);

        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        Iterable<String> speakerMenu = Splitter.on(",").split(Play.configuration.getProperty("speakers"));

        render(speakers, talks, years, speakerMenu);
    }

    private static Query<Speaker> createQuery(String letters) {
        //create a mongo query for finding speakers with lastnames ending in a specific range
        //split the argument and iterate.

        Datastore ds = MorphiaPlugin.ds();
        Query<Speaker> q = ds.createQuery(Speaker.class);
        List<String> parts = Lists.newArrayList(Splitter.on("-").split(letters));

        int startIndex = alphabet.indexOf(parts.get(0));
        int endIndex = alphabet.indexOf(parts.get(1));

        Criteria[] crits = new Criteria[(endIndex - startIndex) + 1];
        int x = 0;
        for(int i = startIndex; i <= endIndex; i++) {
            crits[x++] = q.criteria("lastname").startsWithIgnoreCase(alphabet.get(i));
        }

        q.or(crits);
        return q;
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
