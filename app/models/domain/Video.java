package models.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import play.modules.morphia.Model;

@Entity
public class Video extends Model {

    private String talkAbstract;
    private String title;

    @Id
    private int id;

    public Video() {

    }

    public Video(VimeoVideo vVideo) {
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
}
