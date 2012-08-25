package controllers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import helpers.TagHelper;
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

import static com.google.common.collect.Collections2.transform;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-29
 */
public class Talks extends Controller {

    private static final String INDEX_TEMPLATE = "Application/index.html";

    public static void show(@Required int id) {
        Talk talk = Talk.find("byId", id).first();

        if(talk == null) {
            notFound("The talk was not found. Sorry");
        }

        List<String> filterTags = findFilterTags(transform(talk.tags(), Tag.name()));
        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        Iterable<String> speakerMenu = Splitter.on(",").split(Play.configuration.getProperty("speakers"));

        List<Talk> otherTalks = findOtherTalksByTags(talk);

        render(talk, filterTags, years, speakerMenu, otherTalks);
    }

    public static void filter(@Required int year) {
        List<Talk> talks = Talk.filter("year =", year).filter("type =", "jz").order("-plays").asList();

        if(talks == null || talks.size() == 0) {
            notFound("No talks found for that year. Sorry");
        }

        List<String> tags = TagHelper.findTagsForTalks(talks);

        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        Iterable<String> speakerMenu = Splitter.on(",").split(Play.configuration.getProperty("speakers"));
        renderTemplate(INDEX_TEMPLATE, talks, tags, years, speakerMenu);
    }

    public static void filterByTag(@Required String tag) {
        List<Talk> talks = Talk.filter("tags.name", tag).filter("type =", "jz").order("-plays").asList();

        if(talks == null || talks.size() == 0) {
            notFound("No talks found for that tag. Sorry");
        }

        List<String> tags = TagHelper.findTagsForTalks(talks);

        Iterable<String> years = Splitter.on(",").split(Play.configuration.getProperty("years"));
        Iterable<String> speakerMenu = Splitter.on(",").split(Play.configuration.getProperty("speakers"));

        renderTemplate(INDEX_TEMPLATE, talks, tags, years, speakerMenu);
    }

    private static List<Talk> findOtherTalksByTags(Talk talk) {
        Collection<String> tagNames = Lists.newArrayList(Talk.findTags().apply(talk));
        tagNames = Collections2.filter(tagNames, new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String s) {
                if(s.matches("English") || s.matches("Norsk") || s.matches("Presentation") ||
                        s.matches("LightningTalk") || s.matches("Frameworks")) {
                    return false;
                }
                return true;
            }
        });

        return Talk.filter("tags.name in", tagNames).filter("title !=", talk.title())
                    .order("-plays").limit(8).asList();
    }

    private static List<String> findFilterTags(Collection<String> tags) {
        String filterString = Play.configuration.getProperty("twitter.filter.tags");
        final List<String> filters = Arrays.asList(filterString.split(","));

        Collection<String> list = transform(Collections2.filter(tags, new Predicate<String>() {
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
