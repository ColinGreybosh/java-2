package me.colingreybosh.worldpopulationcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.util.Half.NaN;
import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity {

    private static long CURRENT_POP = (long) 7.6017e9;
    private static double CURRENT_GROWTH = 0.0109;
    private static int CURRENT_YEAR = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvPop = findViewById(R.id.tvPop);
        TextView tvGrowth = findViewById(R.id.tvGrowth);

        tvPop.setText(new DecimalFormat("#,###").format(CURRENT_POP));
        tvGrowth.setText(new DecimalFormat("0.00 %").format(CURRENT_GROWTH));

        Button btnEstimate = findViewById(R.id.btnEstimate);
        btnEstimate.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                estimate();
            }
        });

        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                clear();
            }
        });
    }

    private void estimate()
    {
        EditText etYear = findViewById(R.id.etYear);
        long t;
        try {
            t = pert(CURRENT_POP, CURRENT_GROWTH, getInput(etYear) - CURRENT_YEAR);
        } catch (Exception e) {
            Toast.makeText(this,
                    getResources().getString(R.string.error_year),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        TextView tvPopEstLabel = findViewById(R.id.tvPopEstLabel);
        tvPopEstLabel.setText(getResources().getString(R.string.label_estimatedPop));

        TextView tvEstPop = findViewById(R.id.tvEstPop);
        String formattedPop = new DecimalFormat("#,###").format(t);
        tvEstPop.setText(formattedPop);

    }

    private void clear()
    {
        EditText etYear = findViewById(R.id.etYear);
        TextView tvPopEstLabel = findViewById(R.id.tvPopEstLabel);
        TextView tvEstPop = findViewById(R.id.tvEstPop);

        etYear.setText("");
        tvPopEstLabel.setText("");
        tvEstPop.setText("");
    }

    private long pert(long p, double r, long t)
    {
        return round(p*Math.exp(r*t));
    }

    private long getInput(EditText editText)
    {
        return Integer.parseInt(editText.getText().toString());
    }
}
