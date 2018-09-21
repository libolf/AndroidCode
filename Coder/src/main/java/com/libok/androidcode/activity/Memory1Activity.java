package com.libok.androidcode.activity;

import android.os.Handler;
import android.os.Bundle;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import java.util.Timer;
import java.util.TimerTask;

public class Memory1Activity extends BaseActivity {

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };
    private Timer mTimer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        mHandler = null;
        mRunnable = null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_memory1;
    }

    @Override
    protected String setActivityTitle() {
        return "内存泄漏1";
    }

    @Override
    protected int setActivityAnim() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mTimer = new Timer() {
        };
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
    protected void restoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void addActivityToList() {
        LApplication.addActivity(this);
    }

    @Override
    protected void removeActivityForList() {
        LApplication.removeActivity(this);
    }
}
