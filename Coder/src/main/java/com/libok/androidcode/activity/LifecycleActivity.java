package com.libok.androidcode.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.view.MySurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifecycleActivity extends AppCompatActivity {

    private static final String TAG = "LifecycleActivity";
    @BindView(R.id.lifecycle_skip_dialog_activity)
    Button mLifecycleSkip;
    @BindView(R.id.lifecycle_skip_dialog)
    Button mLifecycleSkipDialog;

    HandlerThread handlerThread = new HandlerThread("handle");
    @BindView(R.id.mysurface)
    MySurfaceView mSurfaceView;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        ButterKnife.bind(this);
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Log.e(TAG, "handleMessage: " + msg.arg1 + " " + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
                }
            }
        };
        mHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
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
        mSurfaceView.animationControl(false);
        Log.e(TAG, "onDestroy: ");
    }

    @OnClick({R.id.lifecycle_skip_dialog_activity, R.id.lifecycle_skip_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lifecycle_skip_dialog_activity:
//                startActivity(new Intent(this, SkipActivity.class).putExtra("book", new Book("qwe", "asd", 12)));
                mSurfaceView.animationControl(false);
                break;
            case R.id.lifecycle_skip_dialog:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Title");
//                builder.setMessage("Message");
//                builder.show();
                mSurfaceView.animationControl(true);
                break;
        }
    }
}
