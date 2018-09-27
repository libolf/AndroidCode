package com.libok.androidcode.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;
import com.libok.androidcode.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifecycleActivity extends BaseActivity {

    private static final String TAG = "LifecycleActivity";

    @BindView(R.id.lifecycle_dialog_activity_button)
    Button mLifecycleSkip;
    @BindView(R.id.lifecycle_dialog_button)
    Button mLifecycleSkipDialog;
    @BindView(R.id.lifecycle_skip_button)
    Button mLifecycleSkipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("intent is ");
        if (intent != null) {
            stringBuilder.append("not null, ");
            stringBuilder.append("extra is ");
            stringBuilder.append(intent.getStringExtra(SkipActivity.EXTRA_KEY));
        } else {
            stringBuilder.append("null");
        }
        Log.e(TAG, "onResume: " + stringBuilder.toString());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e(TAG, "onNewIntent: " + intent.getStringExtra(SkipActivity.EXTRA_KEY));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_lifecycle;
    }

    @Override
    protected String setActivityTitle() {
        return Constants.ActivityConst.ACTIVITY_LIFECYCLE_TITLE;
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

    @OnClick({R.id.lifecycle_dialog_activity_button, R.id.lifecycle_dialog_button, R.id.lifecycle_skip_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lifecycle_dialog_activity_button:
                startAnimationActivity(new Intent(this, DialogActivity.class));
                break;
            case R.id.lifecycle_dialog_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Activity生命周期");
                builder.setMessage("弹出Dialog不会对回调任何的生命周期方法");
                builder.setPositiveButton("确定", null);
                builder.show();
                break;
            case R.id.lifecycle_skip_button:
                startAnimationActivity(new Intent(this, SkipActivity.class));
                break;
        }
    }
}
