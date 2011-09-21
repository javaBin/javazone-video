package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */

import java.util.ArrayList;
import java.util.List;

/**
 * "id":"28803302",
      "is_hd":"1",
      "is_transcoding":"",
      "is_watchlater":"0",
      "license":"0",
      "privacy":"anybody",
      "title":"Deploying Apps on Heroku",
 */

public class VimeoVideo {
    private String title = "";
    private int id;
    private String description;
    private int duration;
    private List<VimeoTag> tags;

    public VimeoVideo() {
        tags = new ArrayList<VimeoTag>();
    }

    public void title(final String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }


    public int id() {
        return id;
    }

    public void id(final Integer id) {
        this.id = id;
    }

    public String description() {
        return description;
    }

    public void description(final String description) {
        this.description = description;
    }

    public int duration() {
        return duration;
    }

    public void duration(int duration) {
        this.duration = duration;
    }

    public List<VimeoTag> tags() {
        return tags;
    }

    public void addTag(final VimeoTag vTag) {
        tags.add(vTag);
    }
}
