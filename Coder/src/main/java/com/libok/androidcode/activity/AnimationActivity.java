package com.libok.androidcode.activity;

import android.animation.IntEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author libok 2018/6/26 00261:47
 * Android动画 https://www.jianshu.com/p/48554844a2db
 */
public class AnimationActivity extends BaseActivity {

    private static final String TAG = "AnimationActivity";

    @BindView(R.id.animation_alpha_button)
    Button mAnimationButtonAlpha;
    @BindView(R.id.animation_scale_button)
    Button mAnimationButtonScale;
    @BindView(R.id.animation_rotate_button)
    Button mAnimationButtonRotate;
    @BindView(R.id.animation_translate_button)
    Button mAnimationButtonTranslate;
    @BindView(R.id.animation_custom_button)
    Button mAnimationDiyButton;
    @BindView(R.id.animation_special_animator_button)
    Button mSpecialAnimatorButton;
    @BindView(R.id.animation_num_text)
    TextView mNumberText;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_animation;
    }

    @Override
    protected String setActivityTitle() {
        return "动画合集";
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

    @OnClick({R.id.animation_alpha_button, R.id.animation_scale_button, R.id.animation_rotate_button, R.id.animation_translate_button, R.id.animation_custom_button, R.id.animation_special_animator_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.animation_alpha_button:
                alphaAnimation();
                break;
            case R.id.animation_scale_button:
                scaleAnimation();
                break;
            case R.id.animation_rotate_button:
                rotateAnimation();
                break;
            case R.id.animation_translate_button:
                translateAnimation();
                break;
            case R.id.animation_custom_button:
                diyAnimation();
                break;
            case R.id.animation_special_animator_button:
                charAnimator();
                break;
        }
    }

    private void diyAnimation() {

    }

    private void alphaAnimation() {
        Animation animation = new AlphaAnimation(1.0f, 0.5f);
        animation.setDuration(2000);
        mAnimationButtonAlpha.startAnimation(animation);
    }

    private void rotateAnimation() {
//        Animation animation = new RotateAnimation(0, 180, 0.5f, 0.5f);
//        animation.setDuration(2000);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        mAnimationButtonRotate.startAnimation(animation);
    }

    private void scaleAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f);
        animation.setDuration(2000);
        mAnimationButtonScale.startAnimation(animation);
    }

    private void translateAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        mAnimationButtonTranslate.startAnimation(animation);

        ValueAnimator integerValueAnimator = ValueAnimator.ofObject(new IntEvaluator() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                Log.e(TAG, "evaluate: " + fraction);
                return (int) (fraction * 100);
            }
        }, 0, 100);
        integerValueAnimator.setDuration(2000);
        integerValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "onAnimationUpdate:222 " + animation.getAnimatedValue());
            }
        });

//        integerValueAnimator.start();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "onAnimationUpdate: " + animation.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.start();
    }

    private void charAnimator() {
        ValueAnimator valueAnimator1 = ValueAnimator.ofObject(new CharEvaluator(), 'A', 'Z');
        valueAnimator1.setDuration(5000);
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "onAnimationUpdate: " + String.valueOf(animation.getAnimatedValue()));
                mNumberText.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        valueAnimator1.start();
    }

    /**
     * 自定义动画进度计算器
     */
    private class CharEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int start = startValue;
            int end = endValue;
            int value = (int) (start + fraction * (end - start));
            return (char) value;
        }
    }

    /**
     * 自定义View动画
     */
    private class LAnimation extends Animation {

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
        }
    }

}
