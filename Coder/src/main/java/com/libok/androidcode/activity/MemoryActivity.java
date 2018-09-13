package com.libok.androidcode.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.libok.androidcode.R;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemoryActivity extends AppCompatActivity {

    @BindView(R.id.memory_1)
    Button mMemory1;
    @BindView(R.id.memory_2)
    Button mMemory2;

    public static Activity sActivity;
    private Timer mTimer;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.memory_1, R.id.memory_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.memory_1:
                startActivity(new Intent(this, Memory1Activity.class));
                break;
            case R.id.memory_2:
                startActivity(new Intent(this, Memory2Activity.class));
                break;
        }
    }
}
