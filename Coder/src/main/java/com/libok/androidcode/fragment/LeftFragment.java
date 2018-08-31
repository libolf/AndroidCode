package com.libok.androidcode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.libok.androidcode.R;
import com.libok.androidcode.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author liboK
 * @date 2018/6/26 0026 10:31
 * @e-mail libolf@outlook.com
 * @description
 */
public class LeftFragment extends Fragment {

    private static final String TAG = "LeftFragment";
    @BindView(R.id.toolbar_fits_title)
    TextView mToolbarFitsTitle;
    @BindView(R.id.toolbar_fits)
    Toolbar mToolbarFits;
    Unbinder unbinder;

    private View mRootView;

    public static LeftFragment newInstance() {

        Bundle args = new Bundle();

        LeftFragment fragment = new LeftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_left, null);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        unbinder = ButterKnife.bind(this, mRootView);
        mToolbarFits.setPadding(0, StatusBarUtil.getStatusBarHeight(activity), 0,0);
        activity.setSupportActionBar(mToolbarFits);
        activity.getSupportActionBar().setTitle("");
//        StatusBarUtil.immerseStatusBar(activity);
        mToolbarFitsTitle.setText("Left");
        Log.e(TAG, "onCreateView: " + mToolbarFits.getPaddingTop() + " " + mToolbarFits.getPaddingBottom() + " " + mToolbarFits.getHeight());
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
