package me.colingreybosh.nestprotect;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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
    private String bearerAuthToken = null;

    /**
     * Constructor for Query class which takes a string literal as a second argument
     *
     * @param context Context of the activity which the Query is instantiated
     * @param queryStr String of the URL which the Query will receive a response
     * @param args Any optional arguments to format queryStr with String.format()
     */
    Query(Context context, String queryStr, String... args)
    {
        setContext(context);
        setRes();
        setQueryStr(queryStr, args);
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
        setContext(context);
        setRes();
        setQueryStr(queryID, args);
    }

    private Query(){}

    /**
     * Sets queryStr equal to the formatted input
     *
     * @param queryStr String of the URL which the Query will receive a response
     * @param args Any optional arguments to format queryStr with String.format()
     */
    public void setQueryStr(String queryStr, String... args)
    {
        this.queryStr = String.format(queryStr, (Object[]) args);
    }

    /**
     * Sets queryStr equal to the formatted input
     *
     * @param queryID Resource ID of the URL which the Query will receive a response
     * @param args Any optional arguments to format queryStr with String.format()
     */
    public void setQueryStr(int queryID, String... args)
    {
        setQueryStr(res.getString(queryID, (Object[]) args));
    }

    /**
     * Sets context equal to the context of the parent activity
     *
     * @param context Context of the activity which the Query is instantiated
     */
    public void setContext(Context context)
    {
        this.context = context;
    }

    private void setRes()
    {
        res = context.getResources();
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
     * @param authToken String of the authentication token without a 'Bearer ' prefix
     */
    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
        bearerAuthToken = "Bearer ".concat(authToken);
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
//            Toast.makeText(context, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
        }
        return responseStr;
    }

    /**
     * Runnable implementation function. Doesn't need to be run manually as this
     * class should be passed as the argument to a Thread constructor.
     */
    public void run()
    {
        try
        {
            responseStr = getResponse(
                queryStr,
                authType,
                bearerAuthToken);
        } catch (Exception e)
        {
//            Toast.makeText(context, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
        }
    }

    private String getResponse(String queryURL, Auth auth, String... token) throws IOException
    {
        URL url;
        url = new URL(queryURL);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        if (auth == Auth.OAUTH2 && token.length == 1)
        {
            connection.setRequestProperty("authorization", token[0]);
            connection.setRequestProperty("content-type", "application/json");
            connection.setInstanceFollowRedirects(true);

            Log.d("API", token[0]);
            Log.d("API", String.valueOf(connection.getResponseCode()));
            Log.d("API", connection.getResponseMessage());
        }
        connection.connect();

        InputStream is = connection.getErrorStream();
        if (is == null)
        {
            is = connection.getInputStream();
        }

        return getStringBuilderFromInputStream(is).toString();
    }

    private StringBuilder getStringBuilderFromInputStream(InputStream is) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
        {
            sb.append(line);
        }
        br.close();
        return sb;
    }
}