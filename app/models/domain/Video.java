package models.domain;

import models.domain.vimeo.VimeoVideo;

public class Video {

    private String talkAbstract;
    private String title;

    public Video(String talkAbstract) {
        this.talkAbstract = talkAbstract;
    }

    public Video() {

    }

    public Video(VimeoVideo vVideo) {
        title = vVideo.title();
    }

    public String talkAbstract() {
        return talkAbstract;
    }

    public void talkAbstract(String abs) {
        this.talkAbstract = abs;
    }

    public String title() {
        return title;
    }
}
