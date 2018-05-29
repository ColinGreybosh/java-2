package me.colingreybosh.bloomsburg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        int[] num1Ints =
            (num1ET.getText().toString().length() >= num2ET.getText().toString().length()) ?
                stringToIntArray(num1ET.getText().toString()) :
                stringToIntArray(num2ET.getText().toString());
        int[] num2Ints =
            (num1ET.getText().toString().length() < num2ET.getText().toString().length()) ?
                stringToIntArray(num1ET.getText().toString(), num1Ints.length) :
                stringToIntArray(num2ET.getText().toString(), num1Ints.length);

        int carryCount = 0;
        boolean carry = false;
        for (int i = num1Ints.length - 1; i >= 0; i--)
        {
            int sum = num1Ints[i] + num2Ints[i];
            if (carry)
                sum++;
            if (sum > 9)
            {
                carry = true;
                carryCount++;
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

    private int[] stringToIntArray(String str, int len)
    {
        int[] output = new int[len];
        int[] ints = stringToIntArray(str);
        for (int i = 0; i < ints.length; i++)
        {
            output[output.length - 1 - i] = ints[ints.length - 1 - i];
        }
        return output;
    }
}
