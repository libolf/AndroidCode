package com.libok.androidcode.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.libok.androidcode.R;
import com.libok.androidcode.interf.IBaseView;
import com.libok.androidcode.presenter.MvpPresenter;
import com.libok.androidcode.util.L;

public class MVPActivity extends AppCompatActivity implements IBaseView {

    private static final String TAG = "MVPActivity";

    private MvpPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        mPresenter = new MvpPresenter();
        mPresenter.attachView(this);
        L.e(TAG, "onCreate: " + mPresenter.isAttach());
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("加载中……");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mPresenter.getData();
    }

    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showData(String data) {
        L.e(TAG, "showData: " + data);
    }

    @Override
    public void showFailure(String msg) {
        L.e(TAG, "showFailure: " + msg);
    }

    @Override
    public void showError(Throwable throwable) {
       L.e(TAG, "showError：", throwable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
