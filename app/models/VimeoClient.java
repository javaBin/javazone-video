package models;

import models.domain.VimeoVideo;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VimeoApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    private JSONMapper mapper;
    private Integer totalVideos;

    public List<VimeoVideo> getVideosByYear(String year, Map<String, String> args, Integer max ) {

        if(supportedTags.containsKey(year)) {
            args.put("tag", supportedTags.get(year));
        } else {
            throw new IllegalArgumentException("year " + year + " is not supported. Must be on of " +
                                               supportedTags.keySet().toString());
        }

        List<VimeoVideo> videos = new ArrayList<VimeoVideo>();

        videos.addAll(getPage(1, args, videos));

        int total = (max == 0)? totalVideos : max;
        addPagesUntilTotal(args, videos, total);

        return videos;
    }

    private void addPagesUntilTotal(Map<String, String> args, List<VimeoVideo> videos, int total) {
        int page = 2;
        while (videos.size() < total) {
            String json = getVideoPage(page++, args);
            videos.addAll(new JSONMapper(json).videosToObjects());
        }
    }

    private List<VimeoVideo> getPage(int pageNumber, Map<String, String> args, List<VimeoVideo> videos) {
        String videoJSON = getVideoPage(pageNumber, args);
        JSONMapper mapper = new JSONMapper(videoJSON);
        totalVideos = mapper.getTotalVideos();
        return mapper.videosToObjects();
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
            for(Entry<String, String> arg : args.entrySet()) {
                request.addQuerystringParameter(arg.getKey(), arg.getValue());
            }
        }
    }
}
