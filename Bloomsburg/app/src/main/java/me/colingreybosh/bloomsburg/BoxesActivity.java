package me.colingreybosh.bloomsburg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BoxesActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxes);
    }

//    public void onClick(View view)
//    {
//        int boxCount = 35;
//
//        while (boxCount != 0)
//        {
//            for (int i = 1 ;; i++)
//            {
//
//            }
//        }
//    }
//
//    private int[] getStacks(int boxes)
//    {
//
//    }

    private int getStackSize(int height)
    {
        int sum = 0;
        for (int i = 1; i < height; i++)
        {
            sum += (1 + 2*(i - 1));
        }
        return sum;
    }
}
