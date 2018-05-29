package me.colingreybosh.bloomsburg;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class BoxesActivity extends AppCompatActivity
{
    private Resources res;
    private EditText inputET;
    private TextView outputTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxes);

        res = this.getResources();
        inputET = findViewById(R.id.inputET);
        outputTV = findViewById(R.id.outputTV);
    }

    public void onClick(View view)
    {
        int boxCount = Integer.valueOf(inputET.getText().toString());
        //int boxCount = 35;
        ArrayList<Integer> stacks = new ArrayList<>();
        do
        {
            int height = 1;
            int stackSize = getStackSize(height);
            while (stackSize <= boxCount) {
                height++;
                stackSize = getStackSize(height);
            }
            height--;
            stackSize = getStackSize(height);
            stacks.add(height);
            boxCount -= stackSize;
        } while (boxCount != 0);

        String outputStr = "";
        String[] ordinals = res.getStringArray(R.array.ordinals);
        for (int i = 0; i < stacks.size(); i++)
        {
            String ordinal = (i < 3) ? ordinals[i] : String.format(ordinals[3], (i+1));
            outputStr += "Height of " + ordinal + " stack: " + stacks.get(i).toString() + "\n";
        }
        outputTV.setText(outputStr);
    }

    private int getStackSize(int height)
    {
        if (height == 0)
            return 0;
        if (height == 1)
            return 1;

        int sum = 0;
        for (int i = 1; i <= height; i++)
        {
            sum += (1 + 2*(i - 1));
        }
        return sum;
    }
}
