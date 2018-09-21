package com.libok.androidcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.libok.androidcode.R;
import com.libok.androidcode.util.Constants;
import com.libok.androidcode.util.StatusBarUtil;

/**
 * @author liboK  2018/09/19 下午 06:40
 * 适配有返回和没有的并且带有标题的Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static final int ACTIVITY_ANIM_TRANSLATE = 1;
    protected static final int ACTIVITY_ANIM_SCALE = 2;
    protected static final int ACTIVITY_ANIM_WX = 3;
    protected static int ACTIVITY_ANIM = ACTIVITY_ANIM_SCALE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        addActivityToList();
        if (setActivityAnim() >= 1 && setActivityAnim() <= 3) {
            ACTIVITY_ANIM = setActivityAnim();
        }

        // 先判断是否是没有返回键的ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar_layout);
        TextView toolbarTitleText;
        ImageView toolbarBackImage = null;
        if (toolbar != null) {
            // 是就找到Title
            toolbarTitleText = findViewById(R.id.toolbar_title_text);
        } else {
            // 再判断是否是有返回键的ToolBar，并找到Title
            toolbar = findViewById(R.id.toolbar_back_layout);
            toolbarTitleText = findViewById(R.id.toolbar_back_title_text);
            toolbarBackImage = findViewById(R.id.toolbar_back_image);
        }

        // 如果以上两步确定了ToolBar不为空
        if (toolbar != null) {
            // 设置沉浸式状态栏
            StatusBarUtil.immerseStatusBar(this);

            setSupportActionBar(toolbar);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle("");
            }
            toolbarTitleText.setText(setActivityTitle());

            if (toolbarBackImage != null) {
                toolbarBackImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ToastUtils.showToast(BaseActivity.this, "退回", Toast.LENGTH_SHORT);
                        onBackPressed();
                        activityBack();
                    }
                });
            }
        }

        initView();
        initData();
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }
    }

    /**
     * 为退出Activity添加动画
     */
    private void activityBack() {
        int[] anim = null;
        switch (ACTIVITY_ANIM) {
            case ACTIVITY_ANIM_TRANSLATE:
                anim = Constants.ActivityConst.ACTIVITY_ANIM_TRANSLATE_BACK;
                break;
            case ACTIVITY_ANIM_SCALE:
                anim = Constants.ActivityConst.ACTIVITY_ANIM_SCALE_BACK;
                break;
            case ACTIVITY_ANIM_WX:
                anim = Constants.ActivityConst.ACTIVITY_ANIM_WX_BACK;
                break;
        }
        if (anim != null) {
            overridePendingTransition(anim[0], anim[1]);
        }
    }

    /**
     * 为进入Activity添加动画
     */
    private void activityGo() {
        int[] anim = null;
        switch (ACTIVITY_ANIM) {
            case ACTIVITY_ANIM_TRANSLATE:
                anim = Constants.ActivityConst.ACTIVITY_ANIM_TRANSLATE_GO;
                break;
            case ACTIVITY_ANIM_SCALE:
                anim = Constants.ActivityConst.ACTIVITY_ANIM_SCALE_GO;
                break;
            case ACTIVITY_ANIM_WX:
                anim = Constants.ActivityConst.ACTIVITY_ANIM_WX_GO;
                break;
        }
        if (anim != null) {
            overridePendingTransition(anim[0], anim[1]);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activityBack();
    }

    @Override
    protected void onDestroy() {
        removeActivityForList();
        super.onDestroy();
    }

    /**
     * @return 设置返回的Layout ID
     */
    protected abstract @LayoutRes
    int setContentViewId();

    /**
     * @return 设置ToolBar Title
     */
    protected abstract String setActivityTitle();

    /**
     * @return 设置Activity动画
     */
    protected abstract int setActivityAnim();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 在初始化时如果是从意外关闭之后启动的会走这个方法，不过最好使用onRestoreInstanceState()方法
     *
     * @param savedInstanceState Activity意外关闭时，保存之前状态的Bundle
     */
    protected abstract void restoreInstanceState(Bundle savedInstanceState);

    /**
     * 添加Activity到Application中的List
     */
    protected abstract void addActivityToList();

    /**
     * 从Application中的List删除当前Activity
     */
    protected abstract void removeActivityForList();

    protected void startAnimationActivity(Intent intent) {
        startActivity(intent);
        activityGo();
    }

}
