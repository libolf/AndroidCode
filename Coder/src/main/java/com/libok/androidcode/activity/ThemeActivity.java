package com.libok.androidcode.activity;

import android.os.Bundle;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

public class ThemeActivity extends BaseActivity {

    @Override
    protected int setContentViewId() {
        return R.layout.activity_theme;
    }

    @Override
    protected String setActivityTitle() {
        return "Theme";
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
