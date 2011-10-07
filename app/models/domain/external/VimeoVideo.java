package models.domain.external;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */

import models.domain.Embed;
import models.domain.Thumbnail;

import java.util.ArrayList;
import java.util.List;

public final class VimeoVideo {
    private final String title;
    private final int id;
    private final String description;
    private final int duration;
    private final List<Tag> tags;
    private final Thumbnail thumb;
    private Embed embed;

    public VimeoVideo(int id, String title, String description, int duration,
                      Thumbnail thumbnail) {
        tags = new ArrayList<Tag>();
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.thumb = thumbnail;
    }

    public String title() {
        return title;
    }

    public int id() {
        return id;
    }

    public String description() {
        return description;
    }

    public int duration() {
        return duration;
    }

    public List<Tag> tags() {
        return tags;
    }

    public void addTag(int id, String content, String url) {
        tags.add(new Tag(id, content, url));
    }

    public Thumbnail thumbnail() {
        return thumb;
    }

    public Embed embedCode() {
        return embed;
    }

    public void addEmbed(Embed embed) {
        this.embed = embed;
    }

    public class Tag {
        private int id;
        private String name;
        private String url;

        public Tag(int id, String content, String url) {
            this.id = id;
            this.name = content;
            this.url = url;
        }

        public int id() {
            return id;
        }

        public String name() {
            return name;
        }

        public String url() {
            return url;
        }
    }

}
