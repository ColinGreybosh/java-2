package me.colingreybosh.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    public static final String RESPONSE_KEY = "RESPONSE_KEY";

    private Context context = this;
    private EditText cityET, stateET;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityET = findViewById(R.id.cityEt);
        stateET = findViewById(R.id.stateEt);

        stateET.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null)
                    {
                        imm.hideSoftInputFromWindow(stateET.getWindowToken(), 0);
                    }

                    try
                    {
                        query = new Query(context,
                                R.string.wunderground_query,
                                Config.getProperty("api.key", getApplicationContext()),
                                stateET.getText().toString().trim(),
                                cityET.getText().toString().trim());
                    } catch (IOException e)
                    {
                        Toast.makeText(context, R.string.query_error, Toast.LENGTH_SHORT).show();
                    }

                    getInput(query);
                    displayResponse(query.getResponseStr());
                    return true;
                }
                return false;
            }
        });
    }

    private void getInput(Query query)
    {
        Thread inputThread = new Thread(query);
        inputThread.start();
        try
        {
            inputThread.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void displayResponse(String responseStr)
    {
        Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
        Bundle b = new Bundle();
        b.putString(RESPONSE_KEY, responseStr);
        i.putExtras(b);
        startActivity(i);
    }
}
