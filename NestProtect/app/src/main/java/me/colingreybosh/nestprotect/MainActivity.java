package me.colingreybosh.nestprotect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view)
    {
        query = new Query(this, R.string.query_url);
        query.setAuthType(Query.Auth.OAUTH2);
        query.setAuthToken(Config.getProperty("token", this));
        String response = query.getResponse();

        TextView tv = findViewById(R.id.textView);
        tv.setText(response);
    }
}
