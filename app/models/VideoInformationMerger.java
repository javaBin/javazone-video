package models;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import models.domain.Speaker;
import models.domain.Tag;
import models.domain.Talk;
import models.domain.TalkTypes;
import models.domain.external.IncogitoSession;
import models.domain.external.VimeoVideo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoInformationMerger {

    public List<Talk> mergeVideoAndSessionInfo(List<VimeoVideo> vimeoVideos, List<IncogitoSession> sessions) {
        List<Talk> talks = new ArrayList<Talk>();
        Map<Integer, Boolean> seen = new HashMap<Integer, Boolean>();

        for(VimeoVideo vVideo : vimeoVideos) {
            int index = titleMatches(vVideo, sessions);

            if(index >= 0) {
                IncogitoSession session = sessions.get(index);
                Talk talk = talkFromVideo(vVideo);
                addSessionInformation(session, talk);
                talks.add(talk);
                seen.put(index, true);
            } else {
                System.err.println("Could not match title for talk <" + vVideo.title() + ">");
            }

        }
        return talks;
    }

    private void addSessionInformation(IncogitoSession session, Talk talk) {
        talk.year(session.year());
        talk.talkAbstract(session.talkAbstract());

        for(IncogitoSession.Speaker speaker : session.speakers()) {
            talk.addSpeaker(new Speaker(speaker.name(), speaker.bio(), speaker.photoURL()));
        }
    }

    public Talk talkFromVideo(VimeoVideo vVideo) {
        Talk talk = new Talk();

        talk.type(TalkTypes.JZ);
        talk.id(vVideo.id());
        talk.title(vVideo.title());
        talk.embed(vVideo.embedCode());
        talk.plays(vVideo.plays());
        talk.comments(vVideo.comments());
        talk.likes(vVideo.likes());

        for(VimeoVideo.Tag tag : vVideo.tags()) {
            talk.addTag(new Tag(tag.id(), tag.name(), tag.url()));
        }
        talk.thumbnail(vVideo.thumbnail());
        return talk;
    }

    private int titleMatches(final VimeoVideo vVideo, List<IncogitoSession> sessions) {

        Predicate<IncogitoSession> exactTitleMatcher = new Predicate<IncogitoSession>() {
            @Override
            public boolean apply(IncogitoSession session) {
                if(StringUtils.isBlank(vVideo.title()) || StringUtils.isBlank(session.title())) {
                    return false;
                }
                return vVideo.title().equalsIgnoreCase(StringUtils.normalizeSpace(session.title()));
            }
        };

        Predicate<IncogitoSession> levenshteinTitleMatcher = new Predicate<IncogitoSession>() {
            @Override
            public boolean apply(IncogitoSession session) {
                return withInEditDistance(vVideo.title(), session.title());
            }

            private boolean withInEditDistance(String videoTitle, String sessionTitle) {
                int distance = StringUtils.getLevenshteinDistance(StringUtils.normalizeSpace(videoTitle),
                        StringUtils.normalizeSpace(sessionTitle));
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
