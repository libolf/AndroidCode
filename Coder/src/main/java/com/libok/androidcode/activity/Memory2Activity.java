package com.libok.androidcode.activity;

import android.os.Bundle;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

public class Memory2Activity extends BaseActivity {

    @Override
    protected int setContentViewId() {
        return R.layout.activity_memory2;
    }

    @Override
    protected String setActivityTitle() {
        return "内存泄漏2";
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
        MemoryActivity.sActivity = this;
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
