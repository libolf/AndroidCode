package com.libok.androidcode.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.libok.androidcode.R;

public class Memory2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory2);
        MemoryActivity.sActivity = this;
    }
}
