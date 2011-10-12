package models;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import models.domain.*;
import models.domain.external.IncogitoSession;
import models.domain.external.VimeoVideo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoInformationMerger {

    public List<Talk> mergeVideoAndSessionInfo(List<VimeoVideo> vimeoVideos, List<IncogitoSession> sessions) {
        List<Talk> talks = new ArrayList<Talk>();

        for(VimeoVideo vVideo : vimeoVideos) {
            Talk talk = new Talk();
            int index = titleMatches(vVideo, sessions);

            if(index >= 0) {
                IncogitoSession session = sessions.get(index);
                talk.talkAbstract(session.talkAbstract());
                talk.id(vVideo.id());
                talk.title(vVideo.title());
                talk.embed(vVideo.embedCode());
                talk.year(session.year());

                for(VimeoVideo.Tag tag : vVideo.tags()) {
                    talk.addTag(new Tag(tag.id(), tag.name(), tag.url()));
                }

                for(IncogitoSession.Speaker speaker : session.speakers()) {
                    talk.addSpeaker(new Speaker(speaker.name(), speaker.bio(), speaker.photoURL()));
                }

                talk.thumbnail(vVideo.thumbnail());

                talks.add(talk);
            } else {
                System.err.println("Could not match title for talk <" + vVideo.title() + ">");
            }

        }

        return talks;
    }

    private int titleMatches(final VimeoVideo vVideo, List<IncogitoSession> sessions) {

        Predicate<IncogitoSession> exactTitleMatcher = new Predicate<IncogitoSession>() {
            @Override
            public boolean apply(IncogitoSession session) {
                if(StringUtils.isBlank(vVideo.title()) || StringUtils.isBlank(session.title())) {
                    return false;
                }
                return vVideo.title().equalsIgnoreCase(session.title());
            }
        };

        Predicate<IncogitoSession> levenshteinTitleMatcher = new Predicate<IncogitoSession>() {
            @Override
            public boolean apply(IncogitoSession session) {
                return withInEditDistance(vVideo.title(), session.title());
            }

            private boolean withInEditDistance(String videoTitle, String sessionTitle) {
                int distance = StringUtils.getLevenshteinDistance(videoTitle, sessionTitle);
                return distance <= 2;
            }
        };


        int index = Iterables.indexOf(sessions, exactTitleMatcher);

        if(index < 0) {
            return Iterables.indexOf(sessions, levenshteinTitleMatcher);
        }

        return index;
    }

}
