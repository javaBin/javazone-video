package controllers;

import com.google.common.base.Splitter;
import models.CollectionTools;
import models.domain.Talk;
import play.Play;
import play.libs.F;
import play.mvc.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2012-03-28
 */
public class Tags extends Controller {
    
    public static void index() {
        Iterable <String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));

        List<Talk> talks = Talk.all().asList();
        List<F.Tuple<String, Integer>> tags = CollectionTools.extractTagsWithCount(talks);

        List<F.T3<String, Integer, String>> cloudTags = new ArrayList<F.T3<String, Integer, String>>();

        for(F.Tuple<String, Integer> tag : tags) {
            F.T3<String, Integer, String> newTag = new F.T3(tag._1, tag._2, normalize(tag._2));
            cloudTags.add(newTag);
        }
        Collections.sort(cloudTags, new AlphabeticComparator());
        Iterable<String> speakerMenu = Splitter.on(",").split(Play.configuration.getProperty("speakers"));

        render(cloudTags, years, speakerMenu);
    }

    private static Object normalize(Integer i) {
        if(i <= 1) {
            return "c1";
        }
        if(i > 1 && i <= 2) {
            return "c2";
        }
        if(i > 2 && i <= 4) {
            return "c3";
        }
        if(i > 4 && i <= 7) {
            return "c4";
        }
        if(i > 7 && i <= 10) {
            return "c5";
        }
        if(i > 10 && i <= 20) {
            return "c6";
        }
        if(i > 30 && i <= 45) {
            return "c7";
        }
        if(i > 45 && i <= 80) {
            return "c8";
        }

        return "c9";
    }

    static class AlphabeticComparator implements Comparator<F.T3<String, Integer, String>> {
        @Override
        public int compare(F.T3<String, Integer, String> o1, F.T3<String, Integer, String> o2) {
            return o1._1.compareTo(o2._1);
        }
    }

}
