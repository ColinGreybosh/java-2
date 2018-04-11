package me.colingreybosh.midterm;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;
import org.random.api.RandomOrgClient;
import org.random.api.exception.RandomOrgInsufficientBitsError;

import java.io.IOException;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback
{
    private static final int DECIMAL_PRECISION = 15;
    private static final float STROKE_SIZE = 6.0f;

    SurfaceView surfaceView;
    EditText dotsET;
    TextView piTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(this);
        dotsET = findViewById(R.id.dotsET);
        piTV = findViewById(R.id.piTV);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        tryDrawing(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {
        tryDrawing(surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {}

    private void tryDrawing(SurfaceHolder holder, Circle... circles)
    {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null)
        {
            draw(canvas, circles);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void draw(Canvas canvas, Circle... circles)
    {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_SIZE);
        paint.setColor(Color.BLACK);

        canvas.drawRGB(0xFF, 0xFF, 0xFF);

        canvas.drawCircle(
                0,
                canvas.getHeight(),
                canvas.getWidth(),
                paint);

        canvas.drawRect(
                0,
                0,
                canvas.getWidth(),
                canvas.getHeight(),
                paint);

        for (Circle circle : circles)
        {
            if (circle.isInUnitCircle)
            {
                paint.setColor(Color.BLUE);
            } else
            {
                paint.setColor(Color.RED);
            }
            canvas.drawCircle(
                    circle.cx,
                    circle.cy,
                    circle.r,
                    paint);
        }
    }

    public void onCalcBtnClicked(View view)
    {
        String input = dotsET.getText().toString();
        if (input.equals("") || input.equals("0"))
        {
            Toast.makeText(this, R.string.input_error, Toast.LENGTH_SHORT).show();
            return;
        }

        // Get user input for number of points
        int numOfPoints = Integer.parseInt(dotsET.getText().toString());
        if (numOfPoints > 10000)
        {
            numOfPoints = 10000;
            dotsET.setText("10000");
        }

        // Initialize and declare client class provided by random.org
        // Pass in API key from config.properties file located at src/main/assets
        RandomOrgClient roc = RandomOrgClient.getRandomOrgClient(Config.getProperty("api.key", this));
        // Declare empty array of user-defined size
        Circle[] circles = new Circle[numOfPoints];
        // Initialize array to hold random point coordinates
        double[] randoms;

        try
        {
            // Call random.org API using their provided library
            // Get (2 * numOfPointsTotal) random decimals
            // Doubles have 15 digits
            // Save returned random ints to an array
            randoms = roc.generateDecimalFractions(
                    2 * numOfPoints,
                    DECIMAL_PRECISION);
        } catch (IOException e)
        {
            Toast.makeText(this, R.string.query_error, Toast.LENGTH_SHORT).show();
            return;
        } catch (RandomOrgInsufficientBitsError e)
        {
            Toast.makeText(this, R.string.insufficient_bits_error, Toast.LENGTH_LONG).show();
            return;
        } catch (Exception e)
        {
            return;
        }

        int numOfPointsInCircle = 0;
        int surfaceScale = surfaceView.getWidth();
        // Use the random ints to set x and y locations for circles
        for (int i = 0; i < circles.length; i++)
        {
            circles[i] = new Circle(
                    randoms[2 * i],
                    randoms[2 * i + 1],
                    (int)(STROKE_SIZE),
                    surfaceScale);
            if (circles[i].isInUnitCircle)
            {
                numOfPointsInCircle++;
            }
        }

        // Pass the points to the draw function
        tryDrawing(surfaceView.getHolder(), circles);

        DecimalFormat format = new DecimalFormat("#.#####");
        double pi = 4.0 * (double)numOfPointsInCircle / (double)numOfPoints;
        piTV.setText(String.format(getResources().getString(R.string.pi), format.format(pi)));
    }

    public void onResetBtnClicked(View view)
    {
        tryDrawing(surfaceView.getHolder());
        dotsET.setText("");
        piTV.setText("");
    }

    public void onExplainBtnClicked(View view)
    {
        Intent intent = new Intent(this, ExplainActivity.class);
        startActivity(intent);
    }
}
