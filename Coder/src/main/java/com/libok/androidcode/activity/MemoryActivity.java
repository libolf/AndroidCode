package com.libok.androidcode.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemoryActivity extends BaseActivity {

    @BindView(R.id.memory_1)
    Button mMemory1;
    @BindView(R.id.memory_2)
    Button mMemory2;

    public static Activity sActivity;
    private Timer mTimer;


    @Override
    protected int setContentViewId() {
        return R.layout.activity_memory;
    }

    @Override
    protected String setActivityTitle() {
        return "内存泄漏检测";
    }

    @Override
    protected int setActivityAnim() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

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

    @OnClick({R.id.memory_1, R.id.memory_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.memory_1:
                startAnimationActivity(new Intent(this, Memory1Activity.class));
                break;
            case R.id.memory_2:
                startAnimationActivity(new Intent(this, Memory2Activity.class));
                break;
        }
    }
}
