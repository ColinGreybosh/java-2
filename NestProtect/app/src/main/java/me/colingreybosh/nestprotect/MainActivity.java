package me.colingreybosh.nestprotect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        Log.d("onClick", "onClick Started");

        query = new Query(this, R.string.query_url);
        query.setAuthType(Query.Auth.OAUTH2);
        query.setAuthToken(Config.getProperty("token", this));
        String response = query.getResponse();

        responseTV.setText(response);

        Log.d("onClick", "onClick Ended");
    }
}
