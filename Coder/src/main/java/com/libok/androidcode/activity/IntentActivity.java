package com.libok.androidcode.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;
import com.libok.androidcode.service.BaskTaskService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntentActivity extends BaseActivity {

    @BindView(R.id.intent_create_chooser_button)
    Button mIntentCreateChooserButton;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_intent;
    }

    @Override
    protected String setActivityTitle() {
        return "Intent";
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 如果被调用，那么Bundle必定不会为null，可选择执行super方法，有针对的恢复销毁前的状态
     * 在onStart方法之后onResume方法之前被调用
     * onCreate方法中Bundle只有在意外销毁时才会不为null，在正常启动的时候是null
     * 如果要恢复状态最好还是在此方法中恢复，免去判空处理
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @OnClick(R.id.intent_create_chooser_button)
    public void onViewClicked() {
//        openAppDetailInMarket("com.tencent.mm");

        startService(new Intent(this, BaskTaskService.class));
        LApplication.finishAllActivity();
    }


    private void openAppDetailInMarket(String packageName) {
        String marketUrl = "market://details?id=" + packageName;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(marketUrl));

        Intent chooser = Intent.createChooser(intent, "选择应用商店");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }

//        try {
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
