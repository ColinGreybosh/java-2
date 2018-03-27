package me.colingreybosh.weather;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity
{
    private Resources res;
//    private String iconStr, locationStr, observeTimeStr, tempStr, weatherStr;
//    private ImageView iconTV;
//    private TextView observeTimeTV, tempTV, weatherTV;

    private static final int FORECAST_LENGTH = 5;

    private String[] weekdayStr = new String[FORECAST_LENGTH];
    private String[] iconStr = new String[FORECAST_LENGTH];
    private String[] highStr = new String[FORECAST_LENGTH];
    private String[] lowStr = new String[FORECAST_LENGTH];

    private TextView[] weekdayTV;
    private ImageView[] iconIV;
    private TextView[] highTV;
    private TextView[] lowTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        res = this.getResources();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weekdayTV = new TextView[]{
            findViewById(R.id.weekday1TV),
            findViewById(R.id.weekday2TV),
            findViewById(R.id.weekday3TV),
            findViewById(R.id.weekday4TV),
            findViewById(R.id.weekday5TV)
        };
        iconIV = new ImageView[]{
            findViewById(R.id.icon1IV),
            findViewById(R.id.icon2IV),
            findViewById(R.id.icon3IV),
            findViewById(R.id.icon4IV),
            findViewById(R.id.icon5IV),
        };
        highTV = new TextView[]{
            findViewById(R.id.high1TV),
            findViewById(R.id.high2TV),
            findViewById(R.id.high3TV),
            findViewById(R.id.high4TV),
            findViewById(R.id.high5TV),
        };
        lowTV = new TextView[]{
            findViewById(R.id.low1TV),
            findViewById(R.id.low2TV),
            findViewById(R.id.low3TV),
            findViewById(R.id.low4TV),
            findViewById(R.id.low5TV),
        };

        Bundle b = getIntent().getExtras();
        if (parseResponse(b))
        {
            displayWeather();
        }
    }

    private boolean parseResponse(Bundle bundle)
    {
        String responseStr = bundle.getString(MainActivity.RESPONSE_KEY);
        //if (!responseStr.contains("current_observation"))
        if (!responseStr.contains("forecast"))
        {

            Toast.makeText(this, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
            return false;
        }

//        JSONObject root, currentObservation, displayLocation;
        JSONObject root, forecast, simpleForecast;
        JSONArray forecastDay;
        try
        {
            root = new JSONObject(responseStr);
            forecast = root.getJSONObject("forecast");
            simpleForecast = forecast.getJSONObject("simpleforecast");
            forecastDay = simpleForecast.getJSONArray("forecastday");
        } catch (JSONException e)
        {
            Toast.makeText(this, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        try
        {
            JSONObject currentDay;
            for (int i = 0; i < FORECAST_LENGTH; i++)
            {
                currentDay = forecastDay.getJSONObject(i);

                weekdayStr[i] = currentDay.getJSONObject("date").getString("weekday_short");
                iconStr[i] = currentDay.getString("icon");
                lowStr[i] = currentDay.getJSONObject("low").getString("fahrenheit");
                highStr[i] = currentDay.getJSONObject("high").getString("fahrenheit");
            }
        } catch (JSONException e)
        {
            Toast.makeText(this, res.getString(R.string.query_error), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void displayWeather()
    {
        for (int i = 0; i < FORECAST_LENGTH; i++)
        {
            weekdayTV[i].setText(weekdayStr[i]);
            setIcon(iconIV[i], iconStr[i]);
            highTV[i].setText(highStr[i]);
            lowTV[i].setText(lowStr[i]);
        }
    }

    private void setIcon(ImageView iconIV, String iconStr)
    {
        iconIV.setImageResource(res.getIdentifier(iconStr, "drawable", getPackageName()));
    }
}
