package me.colingreybosh.bloomsburg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onViewPressed(View view)
    {
        Intent intent = new Intent();
        switch (view.getId())
        {
            case (R.id.okapi):
                intent.setClass(this, OkapiActivity.class);
                break;
            case (R.id.vowel):
                intent.setClass(this, VowelActivity.class);
                break;
            case (R.id.carry):
                intent.setClass(this, CarryActivity.class);
                break;
            case (R.id.boxes):
                intent.setClass(this, BoxesActivity.class);
                break;
            case (R.id.wealth):
                intent.setClass(this, WealthActivity.class);
                break;
            case (R.id.faro):
                intent.setClass(this, FaroActivity.class);
                break;
            case (R.id.back_forth):
                intent.setClass(this, BackForthActivity.class);
                break;
            case (R.id.chain):
                intent.setClass(this, ChainActivity.class);
                break;
            case (R.id.prime):
                intent.setClass(this, PrimeActivity.class);
                break;
            case (R.id.remain):
                intent.setClass(this, RemainActivity.class);
                break;
        }
        startActivity(intent);
    }
}
