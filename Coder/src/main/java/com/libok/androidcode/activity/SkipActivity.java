package com.libok.androidcode.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SkipActivity extends BaseActivity {

    private static final String TAG = "SkipActivity";
    @BindView(R.id.skip_text)
    TextView mSkipText;
    @BindView(R.id.skip_text_change)
    Button mSkipTextChangeButton;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_skip;
    }

    @Override
    protected String setActivityTitle() {
        return "跳转页面";
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
        // 格式化String
        String s = getString(R.string.skip_test_string, 12, 24);
        Log.e(TAG, "onCreate: " + s);
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

    @OnClick(R.id.skip_text_change)
    public void onViewClicked() {
//        CollapsingTextHelper
//        try {
//            Class cl = Class.forName("android.support.design.widget.CollapsingTextHelper");
//            CollapsingTextHelper collapsingTextHelper = cl.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
