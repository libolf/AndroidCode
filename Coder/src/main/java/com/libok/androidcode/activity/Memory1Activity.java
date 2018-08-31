package com.libok.androidcode.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.libok.androidcode.R;

import java.util.Timer;
import java.util.TimerTask;

public class Memory1Activity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory1);

        mTimer = new Timer(){};
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 20000);

//        mHandler.postDelayed(mRunnable, 100000);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 100000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        mHandler = null;
        mRunnable = null;
    }
}
