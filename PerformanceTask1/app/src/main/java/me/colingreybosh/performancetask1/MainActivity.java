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

    private String round(String inputStr)
    {
        // Create a temporary variable with newline characters replaced with spaces
        String spaceStr = inputStr.replaceAll("\n", " ");
        // Set the output string equal to the input
        String outputStr = inputStr + "\n\n";

        // Find the index of the last space in the temporary variable
        int spaceIndex = spaceStr.lastIndexOf(" ");
        while (spaceIndex != -1) // If a space is found, do this code
        {
            // Add the input string minus anything past the last space to the output
            outputStr += inputStr.substring(0, spaceIndex) + "\n\n";
            // Delete anything past the last space in the temp variable
            spaceStr = spaceStr.substring(0, spaceIndex);
            // Find the new index of the next, last space in the temp variable
            spaceIndex = spaceStr.lastIndexOf(" ");
        }
        // Return the output once there are no more spaces to find
        return outputStr;
    }
}
