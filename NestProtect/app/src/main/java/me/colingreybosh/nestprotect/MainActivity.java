package me.colingreybosh.nestprotect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import me.colingreybosh.query.Query;

public class MainActivity extends AppCompatActivity
{
    Query query;
    TextView nameTV, onlineTV, coTV, smokeTV, batteryTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameTV = findViewById(R.id.nameTV);
        onlineTV = findViewById(R.id.onlineTV);
        batteryTV = findViewById(R.id.batteryTV);
        coTV = findViewById(R.id.coTV);
        smokeTV = findViewById(R.id.smokeTV);

        updateValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.refresh_item:
                updateValues();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateValues()
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
            JSONObject json = new JSONObject(response);

            String name = json.getString("name");
            nameTV.setText(name);

            String batteryHealth = json.getString("battery_health");
            switch (batteryHealth)
            {
                case "ok":
                    batteryTV.setText(getString(R.string.ok, "Battery "));
                    batteryTV.setTextColor(getColor(R.color.green));
                    break;
                case "replace":
                    batteryTV.setText(getString(R.string.replace, " Battery"));
                    batteryTV.setTextColor(getColor(R.color.red));
                    break;
                default:
                    batteryTV.setText(getString(R.string.warning, "Battery "));
                    batteryTV.setTextColor(getColor(R.color.red));
                    break;
            }

            String coAlarm = json.getString("co_alarm_state");
            switch (coAlarm)
            {
                case "ok":
                    coTV.setText(getString(R.string.ok, "CO "));
                    coTV.setTextColor(getColor(R.color.green));
                    break;
                case "warning":
                    coTV.setText(getString(R.string.warning, "CO "));
                    coTV.setTextColor(getColor(R.color.orange));
                    break;
                case "emergency":
                    coTV.setText(getString(R.string.emergency, "CO "));
                    coTV.setTextColor(getColor(R.color.red));
                    break;
                default:
                    coTV.setText(R.string.emergency);
                    coTV.setTextColor(getColor(R.color.red));
                    break;
            }

            String smokeAlarm = json.getString("smoke_alarm_state");
            switch (smokeAlarm)
            {
                case "ok":
                    smokeTV.setText(getString(R.string.ok, "Smoke "));
                    smokeTV.setTextColor(getColor(R.color.green));
                    break;
                case "warning":
                    smokeTV.setText(getString(R.string.warning, "Smoke "));
                    smokeTV.setTextColor(getColor(R.color.orange));
                    break;
                case "emergency":
                    smokeTV.setText(getString(R.string.emergency, "Smoke "));
                    smokeTV.setTextColor(getColor(R.color.red));
                    break;
                default:
                    smokeTV.setText(getString(R.string.emergency, "Smoke "));
                    smokeTV.setTextColor(getColor(R.color.red));
                    break;
            }

            boolean isOnline = json.getBoolean("is_online");
            if (isOnline)
            {
                onlineTV.setText(getString(R.string.online));
                onlineTV.setTextColor(getColor(R.color.green));
            }
            else
            {
                onlineTV.setText(getString(R.string.offline));
                onlineTV.setTextColor(getColor(R.color.red));
            }
        } catch (JSONException e)
        {
            Toast.makeText(this, R.string.query_error, Toast.LENGTH_SHORT).show();
        }
    }
}
