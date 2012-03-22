package models.domain;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Function;
import models.domain.external.VimeoVideo;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.transform;

@AutoTimestamp
@Entity
public class Talk extends Model {

    private String talkAbstract;
    private String title;
    private Integer year;
    private Integer plays;
    private Integer likes;
    private Integer comments;

    @Embedded
    private List<Tag> tags;

    @Id
    private int id;

    @Embedded
    private Thumbnail thumbnail;

    @Embedded
    private Embed embed;

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
        embed = vVideo.embedCode();
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

    public Embed embed() {
        return embed;
    }

    public void embed(Embed embed) {
        this.embed = embed;
    }

    public Integer year() {
        return year;
    }

    public void year(Integer year) {
        this.year = year;
    }

    public Integer plays() {
        return plays;
    }

    public void plays(Integer plays) {
        this.plays = plays;
    }

    public Integer likes() {
        return likes;
    }

    public void likes(Integer likes) {
        this.likes = likes;
    }

    public Integer comments() {
        return comments;
    }

    public void comments(Integer comments) {
        this.comments = comments;
    }

    public static Function<Talk, Iterable<String>> findTags() {
        return new Function<Talk, Iterable<String>>() {
            public Iterable<String> apply( Talk talk) {
                return transform(talk.tags, Tag.name());
            }
        };
    }
}
