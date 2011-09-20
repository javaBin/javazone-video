package models;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VimeoApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.Map;
import java.util.Map.Entry;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class VimeoClient {

    private static final String VIMEO_API_URL = "http://vimeo.com/api/rest/v2/";
    private static final String VIDEOS_METHOD = "vimeo.videos.getUploaded";

    private static final Token EMPTY_TOKEN = new Token("", "");
    private static final String CONSUMER_KEY = System.getenv("VIMEO_CONSUMER_KEY");
    private static final String CONSUMER_SECRET = System.getenv("VIMEO_CONSUMER_SECRET");

    public String getAllVideos(Map<String, String> args ) {

        OAuthService service = new ServiceBuilder()
                .provider(VimeoApi.class)
                .apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .build();

        OAuthRequest request = new OAuthRequest(Verb.GET, VIMEO_API_URL);
        addParameters(request, args);

        service.signRequest(EMPTY_TOKEN, request);
        Response response = request.send();

        return response.getBody();
    }

    private void addParameters(OAuthRequest request, Map<String, String> args) {
        request.addQuerystringParameter("method", VIDEOS_METHOD);
        request.addQuerystringParameter("user_id", "javazone");
        request.addQuerystringParameter("full_response", "1");
        request.addQuerystringParameter("format", "json");

        if(! args.isEmpty()) {
            for(Entry<String, String> arg : args.entrySet()) {
                request.addQuerystringParameter(arg.getKey(), arg.getValue());
            }
        }
    }
}
