package com.libok.androidcode.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;
import com.libok.androidcode.view.MySurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurfaceViewActivity extends BaseActivity {

    @BindView(R.id.mysurface)
    MySurfaceView mSurfaceView;
    @BindView(R.id.surface_start)
    Button mSurfaceStart;
    @BindView(R.id.surface_stop)
    Button mSurfaceStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_surfaceview;
    }

    @Override
    protected String setActivityTitle() {
        return null;
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

    @OnClick({R.id.surface_start, R.id.surface_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.surface_start:
                mSurfaceView.animationControl(true);
                break;
            case R.id.surface_stop:
                mSurfaceView.animationControl(false);
                break;
        }
    }
}
