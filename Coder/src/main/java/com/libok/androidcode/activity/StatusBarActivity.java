package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.libok.androidcode.R;
import com.libok.androidcode.fragment.LeftFragment;
import com.libok.androidcode.fragment.MiddleFragment;
import com.libok.androidcode.fragment.RightFragment;
import com.libok.androidcode.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusBarActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.status_viewpager)
    ViewPager mStatusViewpager;
//    @BindView(R.id.top_toolbar)
//    Toolbar mTopToolbar;

    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
        ButterKnife.bind(this);
//        setSupportActionBar(mTopToolbar);
        StatusBarUtil.immerseStatusBar(this);
//        StatusBarUtil.immerseStatusAndNavigationBar(this, mTopToolbar, Color.GREEN);
//        StatusBarUtil.immerseAll(this, null);
//        StatusBarUtil.transparentStatus(this);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(LeftFragment.newInstance());
        mFragmentList.add(MiddleFragment.newInstance());
        mFragmentList.add(RightFragment.newInstance());
        mStatusViewpager.setOffscreenPageLimit(3);
        mStatusViewpager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager()));
        mStatusViewpager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class SimplePagerAdapter extends FragmentPagerAdapter {

        public SimplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}
