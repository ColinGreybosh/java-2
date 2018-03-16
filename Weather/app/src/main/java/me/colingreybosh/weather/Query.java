package me.colingreybosh.weather;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Query implements Runnable
{
    private volatile String responseStr;

    private Resources res;
    private Context context;
    private String queryStr;

    Query(Context context, String queryStr, String... args)
    {
        res = context.getResources();
        this.context = context;
        this.queryStr = String.format(queryStr, (Object[]) args);
    }

    Query(Context context, int queryID, String... args)
    {
        res = context.getResources();
        this.context = context;
        this.queryStr = String.format(res.getString(queryID), (Object[]) args);
    }

    public void run()
    {
        try
        {
            responseStr = getResponseStr(queryStr);
        } catch (IOException e)
        {
            Toast.makeText(context, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
        }
    }

    private String getResponseStr(String queryURL) throws IOException
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

    String getResponseStr()
    {
        return responseStr;
    }
}
