import models.domain.Talk;
import models.domain.Thumbnail;
import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageReturns200() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

    @Test
    public void talkPageResponds200ForValidVideo() {
        Talk talk = new Talk();
        talk.id(123);
        talk.thumbnail(new Thumbnail("", 0, 0));
        talk.save();

        Response response = GET("/talk/123");
        assertIsOk(response);
        assertContentType("text/html", response);

        talk.delete();
    }

    @Test
    public void talkPageResponds404ForInvalidVideo() {
        Response response = GET("/talk/0");
        assertIsNotFound(response);
    }
    
}