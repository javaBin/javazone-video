package models;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import models.domain.IncogitoSession;
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
        assertEquals("<div class=\"wiki\">\n<div class=\"paragraph\">\nMoving from Java to Scala sometimes feels scary. To make the transition a little bit easier it might help to keep some of the existing Java frameworks your company have experience in using. At least that is our theory when we chose to keep SpringMVC as web framework in my current Scala project. Time will tell if we are right or wrong in that theory :)\n</div>\n<div class=\"paragraph\">\nBut I did stumble upon a nice little side effect. That Scala could make Spring Java Config go from cool to great.\n</div>\n<div class=\"paragraph\">\nIn this lightning talk I will show how a Spring config could look in Scala, and how we could use Scala's features to create very readable and type safe Spring configuration.\n</div>\n</div>\n", session.talkAbstract());
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
