package me.colingreybosh.bmicalculator;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Array of input fields
        final EditText[] inputFields =
                {findViewById(R.id.etHeightField),
                findViewById(R.id.etWeightField)};
        // Array of output fields
        final TextView[] outputFields =
                {findViewById(R.id.tvResultNum),
                findViewById(R.id.tvResultCat)};

        // Sets default text hints to imperial
        setInputHint(inputFields[0], R.string.height_hint, "in");
        setInputHint(inputFields[1], R.string.weight_hint, "lb");

        // Add onCheckedChanged Listener to metric/imperial radio group
        RadioGroup rgSystem = findViewById(R.id.rgSystem);
        rgSystem.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            { // NEW RADIO BUTTON WAS SELECTED
                // Always clears input and output fields
                clearInputFields(inputFields);
                clearOutputField(outputFields);
                if (i == R.id.rbImperial)
                {
                    // Sets hints to imperial units
                    setInputHint(inputFields[0], R.string.height_hint, "in");
                    setInputHint(inputFields[1], R.string.weight_hint, "lb");
                } else if (i == R.id.rbMetric)
                {
                    // Sets hints to metric units
                    setInputHint(inputFields[0], R.string.height_hint, "m");
                    setInputHint(inputFields[1], R.string.weight_hint, "kg");
                }
            }
        });

        // Add onClick Listener to button
        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            { // BUTTON WAS CLICKED
                displayBMI();
            }
        });
    }

    public void displayBMI()
    {
        // Gets height/weight text fields
        EditText heightField = findViewById(R.id.etHeightField);
        EditText weightField = findViewById(R.id.etWeightField);

        // Gets input from text fields
        double height = getTextInput(heightField, R.string.error_toast_height);
        if (height == -1)
        { // NO INPUT GIVEN
            return;
        }
        double weight = getTextInput(weightField, R.string.error_toast_weight);
        if (weight == -1)
        { // NO INPUT GIVEN
            return;
        }

        // Gets radio group and finds if the user selected imperial or metric
        RadioGroup rgSystem = findViewById(R.id.rgSystem);
        boolean isMetric = rgSystem.getCheckedRadioButtonId() == R.id.rbMetric;

        // Calculates BMI
        double bmi = calculateBMI(height, weight, isMetric);

        // Makes decimal format for BMI to display one digit past the decimal point
        DecimalFormat df = new DecimalFormat("0.#");

        // Gets a string resource formatted with the BMI
        Resources res = getResources();
        String result_num = res.getString(R.string.result_num, df.format(bmi));
        // Gets text view to display BMI in
        TextView resultNum = findViewById(R.id.tvResultNum);
        // Sets the text view to the string resource
        resultNum.setText(Html.fromHtml(result_num));

        // Gets a string resource from getBMICategory()
        String result_cat = getBMICategory(bmi);
        // Gets text view to show BMI category in
        TextView resultCat = findViewById(R.id.tvResultCat);
        // Sets the text view to the string resource
        resultCat.setText(Html.fromHtml(result_cat));
    }

    /**
     *
     * @param height The height of a person in meters or inches
     * @param weight The weight of a person in kilograms or pounds
     * @param isMetric True if metric system, false if imperial
     * @return Calculated BMI value
     */
    private double calculateBMI(double height, double weight, boolean isMetric)
    {
        return (isMetric)? weight / (height*height) :
                           weight * 703 / (height*height);
    }

    /**
     *
     * @param bmi Decimal value of BMI
     * @return A string specifying what BMI category is associated with the given BMI value
     */
    private String getBMICategory(double bmi)
    {
        Resources res = getResources();
        // Gets a string resource array
        String[] categories = res.getStringArray(R.array.bmi_categories);

        if (bmi < 18.5)
        { // UNDERWEIGHT
            return res.getString(R.string.result_cat, categories[0]);
        } else if (bmi < 25)
        { // NORMAL WEIGHT
            return res.getString(R.string.result_cat, categories[1]);
        } else if (bmi < 30)
        { // OVERWEIGHT
            return res.getString(R.string.result_cat, categories[2]);
        } else if (bmi >= 30)
        { // OBESE
            return res.getString(R.string.result_cat, categories[3]);
        } else
        { // SHOULDN'T HAPPEN
            return "not human";
        }
    }

    /**
     *
     * @param view The EditText field to set the hint of
     * @param resStr The resource string to set the hint with
     * @param formatArgs Any arguments inside the resource string
     */
    private void setInputHint(View view, int resStr, String... formatArgs)
    {
        Resources res = getResources();
        EditText etView = (EditText) view;
        etView.setHint(res.getString(resStr, formatArgs[0]));
    }

    /**
     *
     * @param editTexts Any EditText widgets to clear
     */
    private void clearInputFields(EditText... editTexts)
    {
        // Iterates through all EditText widgets.
        // Sets each one's text to an empty string.
        for (EditText editText : editTexts)
        {
            editText.setText("");
        }
    }

    /**
     *
     * @param textViews Any TextView widgets to clear
     */
    private void clearOutputField(TextView... textViews)
    {
        // Iterates through all TextView widgets.
        // Sets each one's text to an empty string.
        for (TextView textView : textViews)
        {
            textView.setText("");
        }
    }

    /**
     *
     * @param editText EditText widget to get input from
     * @param errorMsg Error message to display if unable to parse input
     * @return Parsed double from given EditText field
     */
    private double getTextInput(EditText editText, int errorMsg)
    {
        // Attempts to convert input to integer
        try {
            return Double.parseDouble(editText.getText().toString());
        } catch (NumberFormatException e) {
            // Prompts user if unable to convert string -> integer and returns -1
            Toast.makeText(this,
                    errorMsg,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
}
