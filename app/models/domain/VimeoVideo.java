package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */

import java.util.ArrayList;
import java.util.List;

public final class VimeoVideo {
    private final String title;
    private final int id;
    private final String description;
    private final int duration;
    private final List<VimeoTag> tags;
    private final Thumbnail thumb;

    public VimeoVideo(int id, String title, String description, int duration, Thumbnail thumbnail) {
        tags = new ArrayList<VimeoTag>();
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

    public List<VimeoTag> tags() {
        return tags;
    }

    public void addTag(final VimeoTag vTag) {
        tags.add(vTag);
    }

    public Thumbnail thumbnail() {
        return thumb;
    }
}
