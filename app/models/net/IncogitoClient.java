package models.net;

import models.domain.external.IncogitoSession;
import models.transform.SessionJSONMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class IncogitoClient {

    private static final String ENDPOINT_PROTOCOL = "http";
    private static final String ENDPOINT_HOST = "javazone.no";
    private static final String SESSION_SERVICE_PATH = "/incogito10/rest/events/JavaZone %s/sessions";

    public List<IncogitoSession> getSessionsForYear(int year) {
        HttpClient client = new DefaultHttpClient();
        String responseBody = null;

        try {
            responseBody = executeRequest(year, client);
        } catch (ClientProtocolException e) {
            e.printStackTrace(); //TODO: log?
        } catch (IOException e) {
            e.printStackTrace(); //TODO: log?
        } finally {
            client.getConnectionManager().shutdown();
        }

        return null != responseBody ? mapObjects(responseBody, year) : new ArrayList<IncogitoSession>();
    }

    private List<IncogitoSession> mapObjects(String json, int defaultYear) {
        return new SessionJSONMapper(json).sessionsToObjects(defaultYear);
    }

    private String executeRequest(int year, HttpClient client) throws IOException {
        String url = encode(String.format(SESSION_SERVICE_PATH, year));
        HttpGet httpget = new HttpGet(url);
        httpget.addHeader("Accept", "application/json");
        httpget.addHeader("Accept-Charset", "UTF-8");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return client.execute(httpget, responseHandler);
    }

    private String encode(String input) {
        try {
            return new URI(ENDPOINT_PROTOCOL, ENDPOINT_HOST, input, null).toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
