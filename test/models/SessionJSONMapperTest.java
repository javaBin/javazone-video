package models;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import models.domain.external.IncogitoSession;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-24
 */
public class SessionJSONMapperTest extends UnitTest {

    private static IncogitoSession session;

    @Before
    public void init() throws IOException {
        session = getSession();
    }

    @Test
    public void mapSessionsToListOfSessions() throws Exception {
        String sessionJson = Files.toString(new File("test/testdata/threeSessions.json"), Charsets.UTF_8);
        SessionJSONMapper mapper = new SessionJSONMapper(sessionJson);
        List<IncogitoSession> sessions = mapper.sessionsToObjects();

        assertEquals(3, sessions.size());
    }

    @Test
    public void isWrapperForTitle(){
        assertEquals(" Spring Java Config is cool. Writing it in Scala makes it sexy!", session.title());
    }

    @Test
    public void isWrapperForAbstract(){
        assertEquals("<div class=\"wiki\">\n<div class=\"paragraph\">\nMoving from Java to Scala sometimes feels scary. To make the transition a little bit easier it might help to keep some of the existing Java frameworks your company have experience in using. At least that is our theory when we chose to keep SpringMVC as web framework in my current Scala project. Time will tell if we are right or wrong in that theory :)\n</div>\n<div class=\"paragraph\">\nBut I did stumble upon a nice little side effect. That Scala could make Spring Java Config go from cool to great.\n</div>\n<div class=\"paragraph\">\nIn this lightning talk I will show how a Spring config could look in Scala, and how we could use Scala's features to create very readable and type safe Spring configuration.\n</div>\n</div>\n",
                     session.talkAbstract());
    }

    @Test
    public void isWrapperForSpeakers() {
        assertEquals(1, session.speakers().size());
    }

    @Test
    public void speakersHaveAName() {
        assertEquals("Kaare Nilsen", session.speakers().get(0).name());
    }

    @Test
    public void speakersHaveABio() {
        assertEquals("<div class=\"wiki\">\n" +
                "<div class=\"paragraph\">\n" +
                "Opinionated programmer with a beard at Arktekk\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "Kaare Nilsen is a passionate programmer with more than 15 years of experience from projects in various industries, in both private and government sector. After all these years he is proud to still call himself a programmer. He firmly believes that architecture is important, in fact - way too important to be left to The Architects.\n" +
                "</div>\n" +
                "<div class=\"paragraph\">\n" +
                "Kaare is not afraid of letting his voice be heard, and often speaks about various programming and open source topics at meetups and conferences.\n" +
                "</div>\n" +
                "</div>", session.speakers().get(0).bio());
    }

    @Test
    public void speakersHaveAPhotoUrl() {
        assertEquals("http://javazone.no/incogito10/rest/events/JavaZone%202011/sessions/8ddc3cfd-c787-4f8b-81d7-c45ba3deacc7/speaker-photos/0",
                     session.speakers().get(0).photoURL());
    }

    @Test
    public void returnsEmptyListForNullString() {
        SessionJSONMapper mapper = new SessionJSONMapper(null);
        assertEquals(0, mapper.sessionsToObjects().size());
    }

    private IncogitoSession getSession() throws IOException {
        String sessionJson = Files.toString(new File("test/testdata/threeSessions.json"), Charsets.UTF_8);
        SessionJSONMapper mapper = new SessionJSONMapper(sessionJson);
        return mapper.sessionsToObjects().get(0);
    }
}
