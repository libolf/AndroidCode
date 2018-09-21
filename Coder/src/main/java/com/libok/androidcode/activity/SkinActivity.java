package com.libok.androidcode.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liboK  2018-08-30 0030 下午 07:51
 * <p>
 * PackageName：com.libok.skinplugin
 */
public class SkinActivity extends BaseActivity {

    @BindView(R.id.skin_text1)
    TextView mSkinText1;
    @BindView(R.id.skin_image1)
    ImageView mSkinImage1;
    @BindView(R.id.skin_text2)
    TextView mSkinText2;
    @BindView(R.id.skin_image2)
    ImageView mSkinImage2;
    @BindView(R.id.skin_change_button)
    Button mSkinChange;
    @BindView(R.id.skin_reset_button)
    Button mSkinReset;

    // skinplugin-debug.apk
    private String mSkinApkName = "skinplugin-debug.apk";
    private String mSkinPluginPath = Environment.getExternalStorageDirectory().getPath() + File.separator + mSkinApkName;
    private String mSkinPluginPackageName = "com.libok.skinplugin";

    @Override
    protected int setContentViewId() {
        return R.layout.activity_skin;
    }

    @Override
    protected String setActivityTitle() {
        return "换肤";
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

    @OnClick({R.id.skin_change_button, R.id.skin_reset_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.skin_change_button:
                break;
            case R.id.skin_reset_button:
                break;
        }
    }
}
