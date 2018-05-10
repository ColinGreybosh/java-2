package me.colingreybosh.bloomsburg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VowelActivity extends AppCompatActivity
{
    EditText inputET;
    TextView outputTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vowel);

        inputET = findViewById(R.id.inputET);
        outputTV = findViewById(R.id.outputTV);
    }

    public void onClick(View view)
    {
        String input = inputET.getText().toString();
        StringBuilder output = new StringBuilder();
        char[] str = input.toCharArray();

        char[] vowelsUpper = {'A','E','I','O','U'};
        char[] vowelsLower = {'a','e','i','o','u'};

        for (char c : str)
        {
            boolean isShifted = false;
            for (int i = 0; i < 5; i++)
            {
                if (c == vowelsUpper[i])
                {
                    output.append(vowelsUpper[(i + 1 + 5) % 5]);
                    isShifted = true;
                    break;
                }
                if (c == vowelsLower[i])
                {
                    output.append(vowelsLower[(i + 1 + 5) % 5]);
                    isShifted = true;
                    break;
                }
            }
            if (!isShifted) output.append(c);
        }

        outputTV.setText(output.toString());
    }
}
