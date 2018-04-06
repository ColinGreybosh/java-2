package me.colingreybosh.nestprotect;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <h1>Query</h1>
 * This thread-runnable class pulls response data from an
 * HTML endpoint.
 *
 * @author Colin Greybosh
 * @since 2018-03-26
 */
public class Query implements Runnable
{
    public enum Auth
    {
        OAUTH2,
        NONE
    }

    private volatile String responseStr = null;

    private Resources res;
    private Context context;
    private String queryStr;

    private Auth authType = Auth.NONE;
    private String authToken = null;

    /**
     * Constructor for Query class which takes a string literal as a second argument
     *
     * @param context Context of the activity which the Query is instantiated
     * @param queryStr String of the URL which the Query will receive a response
     * @param args Any optional arguments to format queryStr with String.format()
     */
    Query(Context context, String queryStr, String... args)
    {
        res = context.getResources();
        this.context = context;
        this.queryStr = String.format(queryStr, (Object[]) args);
    }

    /**
     * Constructor for Query class which takes a string resource ID as a second argument
     *
     * @param context Context of the activity which the Query is instantiated
     * @param queryID ID of the URL which the Query will receive a response
     * @param args Any optional arguments to format queryID with String.format()
     */
    Query(Context context, int queryID, String... args)
    {
        res = context.getResources();
        this.context = context;
        this.queryStr = String.format(res.getString(queryID), (Object[]) args);
    }

    /**
     * Runnable implementation function. Doesn't need to be run manually as this
     * class should be passed as the argument to a Thread constructor.
     */
    public void run()
    {
        try
        {
            if (authType == Auth.NONE)
            {
                responseStr = getResponse(queryStr);
            }
            if (authType == Auth.OAUTH2 && authToken != null)
            {
                responseStr = getOAuth2Response(queryStr, authToken);
            }
        } catch (IOException e)
        {
            Toast.makeText(context, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
        }
    }

    private String getOAuth2Response(String queryURL, String token) throws IOException
    {
//        OkHttpClient client = new OkHttpClient.Builder()
//            .authenticator(new Authenticator() {
//                @Override public Request authenticate(Route route, Response response) throws IOException {
//                    return response.request().newBuilder()
//                        .header("Authorization", auth)
//                        .build();
//                }
//            })
//            .followRedirects(true)
//            .followSslRedirects(true)
//            .build();
//
//        Request request = new Request.Builder()
//            .url("https://developer-api.nest.com")
//            .get()
//            .addHeader("content-type", "application/json; charset=UTF-8")
//            .addHeader("authorization", auth)
//            .build();

        URL url;
        url = new URL(queryURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Bearer c.".concat(token));
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }

    /**
     * Sets the type of authentication provided in the HTTP request
     *
     * @param authType Enum of the authentication type (OAuth2 or no authentication)
     */
    public void setAuthType(Auth authType)
    {
        this.authType = authType;
    }

    /**
     * Sets the token set in the authentication header of the HTTP request
     *
     * @param authToken String of the authentication token without a 'Bearer c.' prefix
     */
    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }

    /**
     * Returns the current authentication method (OAuth2 or no authentication)
     *
     * @return The Enum representing the Query's current auth method
     */
    public Auth getAuthType()
    {
        return authType;
    }

    /**
     * Returns the current authentication token
     *
     * @return The String of the authentication token placed in the HTTP header
     */
    public String getAuthToken()
    {
        return authToken;
    }


    private String getResponse(String queryURL) throws IOException
    {
        URL url;
        url = new URL(queryURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }

    /**
     * Returns a string of the response received from the HTML endpoint.
     *
     * @return String The string data of the query response
     */
    String getResponse()
    {
        Thread thread = new Thread(this);
        thread.start();
        try
        {
            thread.join();
        } catch (InterruptedException e)
        {
            Toast.makeText(context, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
        }
        return responseStr;
    }
}