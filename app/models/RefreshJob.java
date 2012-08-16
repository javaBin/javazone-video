package models;


import models.domain.Talk;
import models.domain.external.VimeoVideo;
import play.Logger;
import play.Play;
import play.jobs.Every;
import play.jobs.Job;

import java.util.List;

@Every("1h")
public class RefreshJob extends Job {

    /**
     * Refresh the play, likes and comments of videos, run every hour.
     * We select the five oldes videos, look them up on vimeo and store
     * the new values for interaction on the video.
     */

    public void doJob() {
        Logger.debug("Running refresh job");
        List<Talk> talks = Talk.all().order("_modified").
                limit(Integer.valueOf(Play.configuration.getProperty("refresh.num"))).asList();

        VimeoClient vimeo = new VimeoClient();

        for(Talk talk : talks) {
            VimeoVideo video = vimeo.getVideoById(talk.id());
            Logger.info("%s delta: plays=%s, likes=%s, comments=%s", talk.id(),
                    (video.plays() - talk.plays()),
                    (video.likes() - talk.likes()),
                    (video.comments() - talk.comments()));
            talk.comments(video.comments());
            talk.plays(video.plays());
            talk.likes(video.likes());
            talk.save();

        }
    }

}