package com.libok.androidcode.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liboK  2018-08-20 0020 上午 10:41
 * 揭露动画
 */
public class RevealEffectActivity extends BaseActivity {

    private static final String TAG = "RevealEffectActivity";

    @BindView(R.id.reveal_effect_fb)
    FloatingActionButton mRevealEffectFb;
    @BindView(R.id.reveal_effect_view)
    View mRevealEffectView;
    @BindView(R.id.reveal_effect_password)
    EditText mRevealEffectPassword;
    private Animator mCircularReveal;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_reveal_effect;
    }

    @Override
    protected String setActivityTitle() {
        return "揭露动画";
    }

    @Override
    protected int setActivityAnim() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        mRevealEffectFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputType = mRevealEffectPassword.getInputType();
                Log.e(TAG, "onClick: " + inputType);
                if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    mRevealEffectPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mRevealEffectPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
//                Toast.makeText(RevealEffectActivity.this, "111", Toast.LENGTH_SHORT).show();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mCircularReveal = ViewAnimationUtils.createCircularReveal(mRevealEffectView, mRevealEffectFb.getLeft() + mRevealEffectFb.getWidth() / 2, mRevealEffectFb.getTop() + mRevealEffectFb.getHeight() / 2, 0, 2000);
//                    mCircularReveal.setDuration(5000);
//                    mCircularReveal.addListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                        }
//
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            ViewGroup.LayoutParams layoutParams = mRevealEffectView.getLayoutParams();
//                            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                            mRevealEffectView.setLayoutParams(layoutParams);
//                        }
//                    });
//                    mCircularReveal.start();
//                }
            }
        });
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
