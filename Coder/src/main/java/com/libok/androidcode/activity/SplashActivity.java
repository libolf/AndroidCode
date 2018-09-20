package com.libok.androidcode.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.libok.androidcode.R;
import com.libok.androidcode.util.StatusBarUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtil.immerseAll(this);
    }
}
