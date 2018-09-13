package com.libok.androidcode.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.libok.androidcode.R;
import com.libok.androidcode.drawable.ShadowDrawable;
import com.libok.androidcode.util.DisplayUtils;

/**
 * @author liboK
 * @date 2018/6/26 0026 10:31
 * @e-mail libolf@outlook.com
 * @description
 */
public class RightFragment extends Fragment {

    private static final String TAG = "RightFragment";
    private View mRootView;

    public static RightFragment newInstance() {

        Bundle args = new Bundle();

        RightFragment fragment = new RightFragment();
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
        mRootView = inflater.inflate(R.layout.fragment_right, null);
//        unbinder = ButterKnife.bind(this, mRootView);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        mTopToolbar.setPadding(0, StatusBarUtil.getStatusBarHeight(activity), 0, 0);
//        activity.setSupportActionBar(mTopToolbar);
//        activity.getSupportActionBar().setTitle("");
////        StatusBarUtil.immerseStatusBar(activity);
//        mToolbarTitle.setText("Right");
//        Log.e(TAG, "onCreateView: " + mTopToolbar.getPaddingTop() + " " + mTopToolbar.getHeight());

        View view = mRootView.findViewById(R.id.fragment_mine_white_background);
        ShadowDrawable shadowDrawable = new ShadowDrawable(ShadowDrawable.SHAPE_ROUND,
                new int[]{Color.parseColor("#FFFCFCFC")},
                0,
                Color.parseColor("#103447DA"),
                DisplayUtils.dp2px(getActivity(), 5),
                DisplayUtils.dp2px(getActivity(), 1),
                DisplayUtils.dp2px(getActivity(), 5));
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        view.setBackground(shadowDrawable);
        LinearLayout linearLayout = mRootView.findViewById(R.id.fragment_mine_bottom_background);
        linearLayout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewCompat.setBackground(linearLayout, shadowDrawable);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
