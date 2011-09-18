package models.domain.vimeo;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */

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

    public void title(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }


    public int id() {
        return id;
    }

    public void id(Integer id) {
        this.id = id;
    }

    public String description() {
        return description;
    }

    public void description(String description) {
        this.description = description;
    }
}
