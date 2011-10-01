package models.domain;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;
import models.domain.external.VimeoVideo;
import play.modules.morphia.Model;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Talk extends Model {

    private String talkAbstract;
    private String title;

    @Embedded
    private List<Tag> tags;

    @Id
    private int id;

    @Embedded
    private Thumbnail thumbnail;

    @Reference
    private List<Speaker> speakers;

    public Talk() {
        tags = new ArrayList<Tag>();
        speakers = new ArrayList<Speaker>();
    }

    public Talk(VimeoVideo vVideo) {
        this();
        title = vVideo.title();
        id = vVideo.id();
        thumbnail = vVideo.thumbnail();
    }

    public String talkAbstract() {
        return talkAbstract;
    }

    public void talkAbstract(String abs) {
        this.talkAbstract = abs;
    }

    public void title(String title) {
        this.title = title.trim();
    }

    public String title() {
        return title;
    }

    public void id(int id) {
        this.id = id;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public List<Tag> tags() {
        return tags;
    }

    public Thumbnail thumbnail() {
        return thumbnail;
    }

    public void thumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Speaker> speakers() {
        return speakers;
    }

    public void addSpeaker(Speaker speaker) {
        speakers.add(speaker);
    }
}
