package models.domain;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import play.modules.morphia.Model;

import java.util.ArrayList;
import java.util.List;

@Entity()
public class Talk extends Model {

    private String talkAbstract;
    private String title;

    @Embedded
    private List<Tag> tags;

    @Id
    private int id;

    public Talk() {

    }

    public Talk(VimeoVideo vVideo) {
        title = vVideo.title();
        id = vVideo.id();
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
        if(tags == null) {
            tags = new ArrayList<Tag>();
        }
        tags.add(tag);
    }

    public List<Tag> tags() {
        return tags;
    }

}
