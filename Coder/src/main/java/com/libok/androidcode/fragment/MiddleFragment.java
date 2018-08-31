package com.libok.androidcode.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.libok.androidcode.R;
import com.libok.androidcode.drawable.ShadowDrawable;
import com.libok.androidcode.util.DisplayUtils;
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
public class MiddleFragment extends Fragment {

    private static final String TAG = "MiddleFragment";
    @BindView(R.id.toolbar_fits)
    Toolbar mToolbarFits;
    @BindView(R.id.toolbar_fits_title)
    TextView mToolbarTitle;
    Unbinder unbinder;
    private View mRootView;

    public static MiddleFragment newInstance() {

        Bundle args = new Bundle();

        MiddleFragment fragment = new MiddleFragment();
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
        mRootView = inflater.inflate(R.layout.fragment_middle, null);
        unbinder = ButterKnife.bind(this, mRootView);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
////        StatusBarUtil.immerseStatusBar(activity);
//        mToolbarFits.setPadding(0, StatusBarUtil.getStatusBarHeight(activity), 0,0);
//        activity.setSupportActionBar(mToolbarFits);
//        activity.getSupportActionBar().setTitle("");
//        mToolbarTitle.setText("Middle");
//        Log.e(TAG, "onCreateView: " + mToolbarFits.getPaddingTop() + " " + mToolbarFits.getHeight());

        Button button = mRootView.findViewById(R.id.middle_shadow);
        button.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        ShadowDrawable shadowDrawable = new ShadowDrawable(ShadowDrawable.SHAPE_ROUND,
                new int[]{Color.parseColor("#FFFCFCFC")},
                0,
                Color.parseColor("#803447DA"),
                DisplayUtils.dp2px(getActivity(), 10),
                DisplayUtils.dp2px(getActivity(), 50),
                DisplayUtils.dp2px(getActivity(), 5));

        ViewCompat.setBackground(button, shadowDrawable);

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
