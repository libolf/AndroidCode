package com.libok.androidcode.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GifActivity extends BaseActivity {

    private static final String URL = "https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-26/352f1d243122cf52462a2e6cdcb5ed6d.png";

    @BindView(R.id.gif_one_image)
    ImageView mGifOneImage;
    @BindView(R.id.gif_two_image)
    ImageView mGifTwoImage;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_gif;
    }

    @Override
    protected String setActivityTitle() {
        return "Gif";
    }

    @Override
    protected int setActivityAnim() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        Glide.with(this).load(URL).into(mGifTwoImage);
        Glide.with(this).asGif().load(R.drawable.flower).into(mGifOneImage);
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
