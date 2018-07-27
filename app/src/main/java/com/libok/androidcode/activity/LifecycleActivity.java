package com.libok.androidcode.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.libok.androidcode.R;

public class LifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
    }
}
