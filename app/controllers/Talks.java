package controllers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import models.domain.Tag;
import models.domain.Talk;
import play.Play;
import play.data.validation.Required;
import play.mvc.Controller;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

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

        List<String> filterTags = findFilterTags(transform(talk.tags(), Tag.name()));

        render(talk, filterTags);
    }

    private static List<String> findFilterTags(Collection<String> tags) {
        String filterString = Play.configuration.getProperty("twitter.filter.tags");
        final List<String> filters = Arrays.asList(filterString.split(","));

        Collection<String> list = transform(filter(tags, new Predicate<String>() {
            @Override
            public boolean apply(String tagName) {
                return filters.contains(tagName);
            }
        }), new Function<String, String>() {
            @Override
            public String apply(@Nullable String s) {
                return String.format("'#%s'", s);
            }
        });

        return new ArrayList<String>(list);
        
    }
}
