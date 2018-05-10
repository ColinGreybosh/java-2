package me.colingreybosh.bloomsburg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class OkapiActivity extends AppCompatActivity
{
    Spinner die1, die2, die3;
    TextView payoutTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okapi);

        die1 = findViewById(R.id.die1);
        die2 = findViewById(R.id.die2);
        die3 = findViewById(R.id.die3);
        payoutTV = findViewById(R.id.payoutTV);
    }

    public void onClick(View view)
    {
        int[] nums = {
            die1.getSelectedItemPosition() + 1,
            die2.getSelectedItemPosition() + 1,
            die3.getSelectedItemPosition() + 1};

        int payout = 0;

        for (int i = 0; i < nums.length; i++)
        {
            if (nums[i] == nums[(i + 1 + nums.length) % nums.length])
            {
                payout += 2 * nums[i];
                if (nums[i] == nums[(i + 2 + nums.length) % nums.length])
                {
                    payout += nums[i];
                    break;
                }
            }
        }

        payoutTV.setText(String.valueOf(payout));
    }
}
