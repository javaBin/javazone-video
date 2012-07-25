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

        List<F.Tuple<String, String>> cloudTags = new ArrayList<F.Tuple<String, String>>();

        for(F.Tuple<String, Integer> tag : tags) {
            F.Tuple<String, String> newTag = new F.Tuple(tag._1, normalize(tag._2));
            cloudTags.add(newTag);
        }
        Collections.sort(cloudTags, new AlphabeticComparator());

        render(cloudTags, years);
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

        return "c8";
    }

    static class AlphabeticComparator implements Comparator<F.Tuple<String, String>> {
        @Override
        public int compare(F.Tuple<String, String> o1, F.Tuple<String, String> o2) {
            return o1._1.compareTo(o2._1);
        }
    }

}
