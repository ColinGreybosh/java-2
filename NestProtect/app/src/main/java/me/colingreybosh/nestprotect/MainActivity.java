package me.colingreybosh.nestprotect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import me.colingreybosh.query.Query;

public class MainActivity extends AppCompatActivity
{
    Query query;
    TextView responseTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseTV = findViewById(R.id.textView);
    }

    public void onClick(View view)
    {
        query = new Query(this, R.string.query_url);
        query.setAuthType(Query.Auth.OAUTH2);
        query.setAuthToken(Config.getProperty("token", this));
        String response = query.getResponse();

        if (response == null)
        {
            Toast.makeText(this, R.string.query_error, Toast.LENGTH_SHORT).show();
            return;
        }

        try
        {
            JSONObject responseObj = new JSONObject(response);
        } catch (JSONException e)
        {
            Toast.makeText(this, R.string.query_error, Toast.LENGTH_SHORT).show();
        }
    }
}
