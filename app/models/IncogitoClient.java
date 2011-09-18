package models;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-09-12
 */
public class IncogitoClient {

    public String doRequest(String url) {
        HttpClient client = new DefaultHttpClient();
        String responseBody = "";
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept", "application/json");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = client.execute(httpget, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            client.getConnectionManager().shutdown();

        }

        return responseBody.toString();
    }


}
