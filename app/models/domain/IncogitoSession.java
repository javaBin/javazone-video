package models.domain;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-11
 */
public class IncogitoSession {

    private String title = "";
    private String talkAbstract = "";

    public void title(String title) {
        this.title  = title;
    }

    public String title() {
        return title;
    }

    public void talkAbstract(String anAbstract) {
        this.talkAbstract = anAbstract;
    }

    public String talkAbstract() {
        return talkAbstract;
    }
}
