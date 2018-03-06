package me.colingreybosh.tempconversion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final EditText editTexts[] = new EditText[3];
    private final TemperatureHandler temps = new TemperatureHandler(0, Unit.K);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTexts[Unit.F] = findViewById(R.id.etF);
        editTexts[Unit.C] = findViewById(R.id.etC);
        editTexts[Unit.K] = findViewById(R.id.etK);
        setOnFocusChangeListeners();
    }

    private void setOnFocusChangeListeners()
    {
        for (int _i = 0; _i < editTexts.length; _i++)
        {
            final int i = _i;

            editTexts[i].setText(temps.getTempString(i));
            editTexts[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus)
                    {
                        return;
                    }

                    if (isEtInputUpdated(editTexts[i], i))
                    {
                        temps.setTemp(i, getEtInput(editTexts[i])).convertFrom(i);
                        editTexts[i].setText(temps.getTempString(i));

                        final int iPlus1 = (i + 1 + temps.getTemps().length) % temps.getTemps().length;
                        final int iPlus2 = (i + 2 + temps.getTemps().length) % temps.getTemps().length;
                        editTexts[iPlus1].setText(temps.getTempString(iPlus1));
                        editTexts[iPlus2].setText(temps.getTempString(iPlus2));
                    }
                }
            });
        }
    }

    private double getEtInput(EditText editText)
    {
        try
        {
            return Double.parseDouble(editText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, R.string.errorEnterTemp, Toast.LENGTH_SHORT).show();
            editText.setText(temps.tempFormat.format(0.00));
            return 0.00;
        }
    }

    private boolean isEtInputUpdated(EditText editText, int unit)
    {
        return !editText.getText().toString().equals(temps.getTempString(unit));
    }
}
