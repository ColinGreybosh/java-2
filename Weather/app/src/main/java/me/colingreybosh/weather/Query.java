package me.colingreybosh.weather;

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
    private volatile String responseStr = "";

    private Resources res;
    private Context context;
    private String queryStr;

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
            responseStr = getResponse(queryStr);
        } catch (IOException e)
        {
            Toast.makeText(context, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
        }
    }

    private String getResponse(String queryURL) throws IOException
    {
        URL url;
        url = new URL(queryURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
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
    * Returns the response received from the HTML endpoint in the form of a string.
    *
    * @return String The string data of the query response
    */
    String getResponse()
    {
        return responseStr;
    }
}
