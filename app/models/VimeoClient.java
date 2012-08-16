package models;

import models.domain.Embed;
import models.domain.external.VimeoVideo;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VimeoApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import play.Logger;
import play.Play;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class acting as an interface against the vimeo services.
 *
 * It reads configuration from the conf/application.conf file
 * for urls and such, but needs the vimeo consumer key and consumer secret to
 * be set as environment variables for the requests to work.
 *
 * @author Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class VimeoClient {


    private static final String VIMEO_API_URL      = Play.configuration.getProperty("vimeo.api.url");
    private static final String VIMEO_BASE_URL     = Play.configuration.getProperty("vimeo.base.url");
    private static final String ENDPOINT_HOST      = Play.configuration.getProperty("vimeo.endpoint.host");
    private static final String EMBED_SERVICE_PATH = Play.configuration.getProperty("vimeo.embed.servicepath");
    private static final String VIDEOS_METHOD      = Play.configuration.getProperty("vimeo.video.method");
    private static final String VIDEOS_INFO_METHOD = Play.configuration.getProperty("vimeo.video.info_method");

    private static final String ENDPOINT_PROTOCOL  = "http";

    private static final Token EMPTY_TOKEN = new Token("", "");
    private static final String CONSUMER_KEY = System.getenv("VIMEO_CONSUMER_KEY");
    private static final String CONSUMER_SECRET = System.getenv("VIMEO_CONSUMER_SECRET");
    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";

    private static final Map<String, String> supportedTags = new HashMap<String, String>(){{
            put("2011", "Javazone2011");
            put("2010", "Javazone2010");
            put("2009", "Javazone2009");
            put("2012", "Javazone2012");
        }};

    private Integer totalVideos;

    public VimeoVideo getVideoById(Integer id) {
        Logger.info("getting video information for video id=%s", id);
        String videoJson = getVideoInfo(id);
        VimeoVideo video = new VideoJSONMapper(videoJson).videoToObject();
        video.addEmbed(getEmbed(video.id()));
        return video;
    }

    public List<VimeoVideo> getVideosByYear(String year, Map<String, String> args, Integer max ) {

        if(null == args) {
            args = new HashMap<String, String>();
        }

        if(null == max) {
            max = 0;
        }

        if(supportedTags.containsKey(year)) {
            args.put("tag", supportedTags.get(year));
        } else {
            throw new IllegalArgumentException("year " + year + " is not supported. Must be on of " +
                    supportedTags.keySet().toString());
        }

        List<VimeoVideo> videos = new ArrayList<VimeoVideo>();

        videos.addAll(getPage(1, args));

        int total = (max == 0) ? totalVideos : max;
        addPagesUntilTotal(args, videos, total);

        return videos;
    }

    private void addPagesUntilTotal(Map<String, String> args, List<VimeoVideo> videos, int total) {
        int page = 2;
        while (videos.size() < total) {
            videos.addAll(getPage(page++, args));
        }
    }

    private String getVideoPage(Integer pageNumber, Map<String, String> args) {
        OAuthService service = getService();

        OAuthRequest request = new OAuthRequest(Verb.GET, VIMEO_API_URL);
        args.put("method", VIDEOS_METHOD);
        args.put("page", pageNumber.toString());
        addParameters(request, args);

        return doRequest(service, request);
    }

    private String getVideoInfo(Integer id) {
        OAuthService service = getService();
        OAuthRequest request = new OAuthRequest(Verb.GET, VIMEO_API_URL);
        Map<String, String> args = new HashMap<String, String>();

        args.put("method", VIDEOS_INFO_METHOD);
        args.put("video_id", id.toString());
        addParameters(request, args);

        return doRequest(service, request);
    }

    private String doRequest(OAuthService service, OAuthRequest request) {
        service.signRequest(EMPTY_TOKEN, request);
        Response response = request.send();

        return response.getBody();
    }

    private OAuthService getService() {
        return new ServiceBuilder()
                    .provider(VimeoApi.class)
                    .apiKey(CONSUMER_KEY)
                    .apiSecret(CONSUMER_SECRET)
                    .build();
    }


    private void addParameters(OAuthRequest request, Map<String, String> args) {

        request.addQuerystringParameter("user_id", "javazone");
        request.addQuerystringParameter("full_response", "1");
        request.addQuerystringParameter("format", "json");

        if(! args.isEmpty()) {
            for(Map.Entry<String, String> arg : args.entrySet()) {
                request.addQuerystringParameter(arg.getKey(), arg.getValue());
            }
        }
    }

    private List<VimeoVideo> getPage(int pageNumber, Map<String, String> args) {
        String videoJSON = getVideoPage(pageNumber, args);
        VideoJSONMapper mapper = new VideoJSONMapper(videoJSON);
        totalVideos = mapper.getTotalVideos();
        List<VimeoVideo> videos = mapper.videosToObjects();

        for(VimeoVideo video : videos) {
            video.addEmbed(getEmbed(video.id()));
        }

        return videos;
    }

    private Embed getEmbed(int id) {
        Logger.info("getting embed code for video id=%s", id);
        String videoJson = callEmbedService(id);
        return new EmbedJSONMapper(videoJson).getEmbed();
    }

    private String callEmbedService(int id) {

        HttpClient client = new DefaultHttpClient();
        String responseBody = null;

        try {
            responseBody = executeEmbedRequest(id, client);
        } catch (ClientProtocolException e) {
            Logger.error(e.toString());
        } catch (IOException e) {
            Logger.error(e.toString());
        } finally {
            client.getConnectionManager().shutdown();
        }

        return responseBody;
    }


    private String executeEmbedRequest(int id, HttpClient client) throws IOException {
        String url = encodeUrl(EMBED_SERVICE_PATH);
        url = url + "?url=" + VIMEO_BASE_URL + id + "&maxwidth=800";

        HttpGet httpget = new HttpGet(url);
        httpget.addHeader(HEADER_ACCEPT, "application/json");
        httpget.addHeader(HEADER_ACCEPT_CHARSET, "UTF-8");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return client.execute(httpget, responseHandler);
    }

    private String encodeUrl(String servicePath) {
        try {
            return new URI(ENDPOINT_PROTOCOL, ENDPOINT_HOST, servicePath,
                           null).toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

}
