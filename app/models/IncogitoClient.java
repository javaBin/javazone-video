package models;

import antlr.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class IncogitoClient {

    private static final String ENDPOINT_PROTOCOL = "http";
    private static final String ENDPOINT_HOST = "javazone.no";
    private static final String SESSION_SERVICE_PATH = "/incogito10/rest/events/JavaZone %s/sessions";

    public String getSessionForYear(int year) {
        HttpClient client = new DefaultHttpClient();
        String responseBody = null;

        try {
            String url = encode(String.format(SESSION_SERVICE_PATH, year));
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept", "application/json");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = client.execute(httpget, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace(); //TODO: log?
        } catch (IOException e) {
            e.printStackTrace(); //TODO: log?
        } finally {
            client.getConnectionManager().shutdown();
        }

        return null != responseBody? responseBody.toString() : null;
    }

    private String encode(String input) {
        try {
            return new URI(ENDPOINT_PROTOCOL, ENDPOINT_HOST, input, null).toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
