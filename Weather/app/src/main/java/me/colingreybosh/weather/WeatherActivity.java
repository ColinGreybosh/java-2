package me.colingreybosh.weather;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity
{
    private Resources res;

    private String iconUrlStr;
    private String locationStr;
    private String observeTimeStr;
    private String tempStr;
    private String weatherStr;

    ImageView iconTV;
    TextView observeTimeTV, tempTV, weatherTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        res = this.getResources();

        // TODO: create layout to display info
//        observeTimeTV = findViewById(R.id.observeTimeTV);
//        weatherTV = findViewById(R.id.weatherTV);
//        tempTV = findViewById(R.id.tempTV);
//        iconTV = findViewById(R.id.iconTV);

        Bundle b = getIntent().getExtras();
        displayWeather(b);
    }

    private void displayWeather(Bundle bundle)
    {
        String responseStr = bundle.getString(MainActivity.RESPONSE_KEY);

        JSONObject root, currentObservation = null, displayLocation = null;
        boolean isValidResponse = true;
        try
        {
            root = new JSONObject(responseStr);
        } catch (JSONException e)
        {
            Toast.makeText(this, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
            return;
        }
        try
        {
            currentObservation = root.getJSONObject("current_observation");
            displayLocation = currentObservation.getJSONObject("display_location");
        } catch (JSONException e)
        {
            isValidResponse = false;
        }

        if (isValidResponse)
        {
            try
            {
                iconUrlStr = currentObservation.getString("icon_url");
                locationStr = displayLocation.getString("full");
                observeTimeStr = currentObservation.getString("observation_time");
                tempStr = currentObservation.getString("temperature_string");
                weatherStr = currentObservation.getString("weather");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            getActionBar().setTitle(locationStr);
            observeTimeTV.setText(observeTimeStr);
            tempTV.setText(tempStr);
            weatherTV.setText(weatherStr);
            // TODO: download icon and display
        }
    }
}
