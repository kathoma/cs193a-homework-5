package com.example.kevin.accelerometer1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private float TIME_DURATION = 1000;//duration of time (ms) for which you want to record
    float accX = 0;
    float accY = 0;
    float accZ = 0;
    float xPrev;
    float yPrev;
    float xNew;
    float yNew;
    float xMax;
    float yMax;
    boolean collectOn = false; //true if currently collecting data
    Button collectButton;
    TextView accDisplay;
    Bitmap bmp;
    Canvas bmpCanvas;
    Paint myPaint = new Paint();
    float GRAVITY = (float) 9.81;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        collectButton = (Button) findViewById(R.id.collectButton);
        accDisplay = (TextView) findViewById(R.id.accDisplay);

        myPaint.setARGB(200, 241, 171, 0);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        accX = event.values[0];
        accY = event.values[1];
        accZ = event.values[2];

        //accDisplay.setText(String.format("%.3g%n", accY));
        com.example.kevin.accelerometer1.AccGraphView agv =
                (com.example.kevin.accelerometer1.AccGraphView) findViewById(R.id.accGraphView);

        myPaint.setStrokeWidth(25);
        myPaint.setAlpha(100);
        myPaint.setStyle(Paint.Style.STROKE);

        yMax = agv.getHeight();
        xMax = agv.getWidth();
        yNew = (yMax / 2) + yMax * (accY / GRAVITY)/2;
        xNew = (xMax / 2) - xMax * (accX / GRAVITY)/2;
        bmpCanvas.drawLine(xPrev, yPrev,xNew, yNew, myPaint);
        agv.doTheAnimation(bmp);
        yPrev = yNew;
        xPrev = xNew;
    }

    public void collectClick(View view) {


        if (collectOn == false){ //start collecting
            collectOn = true;
            collectButton.setText("STOP");
            //Register Listener
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

            com.example.kevin.accelerometer1.AccGraphView agv =
                    (com.example.kevin.accelerometer1.AccGraphView) findViewById(R.id.accGraphView);
            bmp = Bitmap.createBitmap(agv.getWidth(), agv.getHeight(), Bitmap.Config.ARGB_8888);
            bmpCanvas = new Canvas(bmp);
            bmpCanvas.drawARGB(255, 255, 255, 255);
            yMax = agv.getHeight();
            xMax = agv.getWidth();
            yPrev = (yMax / 2);
            xPrev = (xMax / 2);
        }
        else { //stop collecting
            collectOn = false;
            collectButton.setText("Fresh Canvas");
            //Unregister Listener
            mSensorManager.unregisterListener(this);
        }

    }

    public void redClick(View view) {
        myPaint.setARGB(100, 205, 30, 16);
    }

    public void blueClick(View view) {
        myPaint.setARGB(100, 70, 170, 240);
    }

    public void yellowClick(View view) {
        myPaint.setARGB(100, 250, 223, 0);
    }
}
