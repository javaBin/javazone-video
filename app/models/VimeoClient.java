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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class VimeoClient {

    private static final String VIMEO_API_URL = "http://vimeo.com/api/rest/v2/";
    private static final String VIDEOS_METHOD = "vimeo.videos.getByTag";

    private static final Token EMPTY_TOKEN = new Token("", "");
    private static final String CONSUMER_KEY = System.getenv("VIMEO_CONSUMER_KEY");
    private static final String CONSUMER_SECRET = System.getenv("VIMEO_CONSUMER_SECRET");

    private static final Map<String, String> supportedTags = new HashMap<String, String>(){{put("2011", "Javazone2011");
        put("2010", "Javazone2010");}};

    private Integer totalVideos;
    private static final String VIMEO_BASE_URL = "http://vimeo.com/";
    private static final String ENDPOINT_PROTOCOL = "http";
    private static final String ENDPOINT_HOST = "vimeo.com";
    private static final String EMBED_SERVICE_PATH = "/api/oembed.json";

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
        OAuthService service = new ServiceBuilder()
                .provider(VimeoApi.class)
                .apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .build();

        OAuthRequest request = new OAuthRequest(Verb.GET, VIMEO_API_URL);
        args.put("page", pageNumber.toString());
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
        String videoJson = callEmbedService(id);
        return new EmbedJSONMapper(videoJson).getEmbed();
    }

    private String callEmbedService(int id) {

        HttpClient client = new DefaultHttpClient();
        String responseBody = null;

        try {
            responseBody = executeRequest(id, client);
        } catch (ClientProtocolException e) {
            e.printStackTrace(); //TODO: log?
        } catch (IOException e) {
            e.printStackTrace(); //TODO: log?
        } finally {
            client.getConnectionManager().shutdown();
        }

        return responseBody;
    }


    private String executeRequest(int id, HttpClient client) throws IOException {
        String url = encodeUrl(id);
        url = url + "?url=" + VIMEO_BASE_URL + id + "&maxwidth=640";

        HttpGet httpget = new HttpGet(url);
        httpget.addHeader("Accept", "application/json");
        httpget.addHeader("Accept-Charset", "UTF-8");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return client.execute(httpget, responseHandler);
    }

    private String encodeUrl(int id) {
        try {
            return new URI(ENDPOINT_PROTOCOL, ENDPOINT_HOST,
                    EMBED_SERVICE_PATH,
                    null).toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }

}
