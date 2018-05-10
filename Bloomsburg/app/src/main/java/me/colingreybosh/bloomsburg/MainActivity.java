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
                intent.setClass(this , OkapiActivity.class);
                break;
            case (R.id.vowel):
                intent.setClass(this , VowelActivity.class);
                break;
            case (R.id.carry):

                break;
            case (R.id.boxes):

                break;
            case (R.id.wealth):

                break;
            case (R.id.faro):

                break;
            case (R.id.back_forth):

                break;
            case (R.id.chain):

                break;
            case (R.id.prime):

                break;
            case (R.id.remain):

                break;
        }
        startActivity(intent);
    }
}
