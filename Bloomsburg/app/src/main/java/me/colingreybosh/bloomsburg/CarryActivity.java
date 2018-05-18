package me.colingreybosh.bloomsburg;

import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class CarryActivity extends AppCompatActivity
{
    EditText num1ET, num2ET;
    TextView carryTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carry);

        num1ET = findViewById(R.id.num1ET);
        num2ET = findViewById(R.id.num2ET);
        carryTV = findViewById(R.id.carryTV);
    }

    public void onClick(View view)
    {
        int[] num1Ints = stringToIntArray(num1ET.getText().toString());
        int[] num2Ints = stringToIntArray(num2ET.getText().toString());

        String n = "";
        for (int i : num1Ints)
        {
            n += String.valueOf(i) + " ";
        }
        Log.d("Array Contents", n);

        n = "";
        for (int i : num2Ints)
        {
            n += String.valueOf(i) + " ";
        }
        Log.d("Array Contents", n);

        int carryCount = 0;
        int length = (num1Ints.length >= num2Ints.length) ? num1Ints.length : num2Ints.length;
        boolean carry = false;
        for (int i = length - 1; i > 0; i--)
        {
            int sum;
            try
            {
                sum = (num1Ints[i] + num2Ints[i]);
            } catch (ArrayIndexOutOfBoundsException e)
            {
                try
                {
                    sum = num1Ints[i];
                } catch (ArrayIndexOutOfBoundsException ee)
                {
                    sum = num2Ints[i];
                }
            }

            if (carry)
                sum++;
            if (sum > 9)
            {
                Log.d("Carry", sum + " carry");
                carry = true;
                carryCount++;
            }
            else
            {
                Log.d("Carry", sum + " no carry");
            }
        }
        carryTV.setText(getResources().getQuantityString(R.plurals.carry_answer, carryCount, carryCount));
    }

    private int[] stringToIntArray(String str)
    {
        String num = String.valueOf(Integer.parseInt(str));
        int length = num.length();
        int[] ints = new int[length];
        for (int i = 0; i < length; i++)
        {
            ints[i] = Integer.valueOf(Character.toString(num.charAt(i)));
        }
        return ints;
    }
}
