package com.example.kevin.accelerometer1;

/**
 * Created by Kevin on 2/26/2016.
 */

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;

import java.util.ArrayList;


public class AccGraphView extends View {

    float xAcc;
    float yAcc;
    float zAcc;
    float GRAVITY = (float) 9.81;
    Bitmap bmp;


    public AccGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected  void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(100, 100,100,100);
        drawSomething(canvas);
    }

    private void drawSomething(Canvas canvas) {
        if (bmp != null) {
            canvas.drawBitmap(bmp, 0, 0, null);
        }
    }

    public void doTheAnimation(final Bitmap b) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    bmp = b;
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                postInvalidate();
            }
        });
        thread.start();
    }
}
