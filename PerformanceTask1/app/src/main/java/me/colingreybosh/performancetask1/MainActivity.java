package me.colingreybosh.performancetask1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView textView;
    String lyrics =
        "Row row row your boat\n" +
        "Gently down the stream\n" +
        "Merrily merrily merrily merrily\n" +
        "Life is but a dream";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }

    public void onClick(View view)
    {
        textView.setText(round(lyrics));
    }

    private String round(String str)
    {
        String input = str.replaceAll("\n", " ");
        String round = str + "\n\n";

        int lastIndex = input.lastIndexOf(" ");
        while (lastIndex != -1)
        {
            round += str.substring(0, lastIndex) + "\n\n";
            input = input.substring(0, lastIndex);
            lastIndex = input.lastIndexOf(" ");
        }
        return round;
    }
}
