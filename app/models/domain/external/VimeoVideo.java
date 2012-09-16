package models.domain.external;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */

import models.domain.Embed;
import models.domain.Thumbnail;
import org.joda.time.DateTime;

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
    private final int plays;
    private final int comments;
    private final int likes;
    private DateTime uploadDate;
    private String privacy;

    public VimeoVideo(int id, String title, String description, int duration,
                      Thumbnail thumbnail, int plays, int comments, int likes, DateTime uploadDate, String privacy) {
        this.plays = plays;
        this.comments = comments;
        this.likes = likes;
        tags = new ArrayList<Tag>();
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.thumb = thumbnail;
        this.uploadDate = uploadDate;
        this.privacy = privacy;
    }

    public int plays() {
        return plays;
    }
    public int comments() {
        return comments;
    }

    public int likes() {
        return likes;
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

    public DateTime uploadDate() {
        return uploadDate;
    }

    public boolean isPrivate() {
        return !"anybody".equals(this.privacy);
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
