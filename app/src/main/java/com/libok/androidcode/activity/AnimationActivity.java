package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.libok.androidcode.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author libok
 * @date 2018/6/26 00261:47
 * @e-mail libo@nebulaera.com
 * @description Android动画 https://www.jianshu.com/p/48554844a2db
 */
public class AnimationActivity extends AppCompatActivity {

    private static final String TAG = "AnimationActivity";
    private static final String URL = "https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-26/352f1d243122cf52462a2e6cdcb5ed6d.png";

    @BindView(R.id.animation_button_alpha)
    Button mAnimationButtonAlpha;
    @BindView(R.id.animation_button_scale)
    Button mAnimationButtonScale;
    @BindView(R.id.animation_button_rotate)
    Button mAnimationButtonRotate;
    @BindView(R.id.animation_button_translate)
    Button mAnimationButtonTranslate;
    @BindView(R.id.image_intent)
    ImageView mImageIntent;
    @BindView(R.id.image_gif)
    ImageView mImageGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        Glide.with(this).load(URL).into(mImageIntent);
        Glide.with(this).asGif().load(R.drawable.flower).into(mImageGif);
    }

    @OnClick({R.id.animation_button_alpha, R.id.animation_button_scale, R.id.animation_button_rotate, R.id.animation_button_translate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.animation_button_alpha:
                alphaAnimation();
                break;
            case R.id.animation_button_scale:
                scaleAnimation();
                break;
            case R.id.animation_button_rotate:
                rotateAnimation();
                break;
            case R.id.animation_button_translate:
                translateAnimation();
                break;
        }
    }

    private void alphaAnimation() {
        Animation animation = new AlphaAnimation(1.0f, 0.5f);
        animation.setDuration(2000);
        mAnimationButtonAlpha.startAnimation(animation);
    }

    private void rotateAnimation() {
        Animation animation = new RotateAnimation(0, 180, 0.5f, 0.5f);
        animation.setDuration(2000);
        mAnimationButtonRotate.startAnimation(animation);
    }

    private void scaleAnimation() {
        Animation animation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f);
        animation.setDuration(2000);
        mAnimationButtonScale.startAnimation(animation);
    }

    private void translateAnimation() {
        Animation animation = new TranslateAnimation(1.0f, 1.1f, 0.5f, 1.5f);
        animation.setDuration(2000);
        mAnimationButtonTranslate.startAnimation(animation);
    }
}
