package models.domain.external;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */
public final class IncogitoSession {

    private final String title;
    private final String talkAbstract;
    private final List<Speaker> speakers;

    public IncogitoSession(String title, String abs) {
        this.title = title;
        this.talkAbstract = abs;
        speakers = new ArrayList<Speaker>();
    }

    public String title() {
        return title;
    }

    public String talkAbstract() {
        return talkAbstract;
    }

    public List<Speaker> speakers() {
        return speakers;
    }

    public void addSpeaker(String name, String bio, String url) {
        speakers.add(new Speaker(name, bio, url));
    }

    public class Speaker {
        private final String name;
        private final String bio;
        private final String photoURL;

        public Speaker(String name, String bio, String url) {
            this.name = name;
            this.bio = bio.trim();
            this.photoURL = url;
        }

        public String name() {
            return name;
        }

        public String bio() {
            return bio;
        }

        public String photoURL() {
            return photoURL;
        }
    }
}
