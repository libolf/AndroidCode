package com.libok.androidcode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class RightFragment extends Fragment {

    private static final String TAG = "RightFragment";
    @BindView(R.id.right_toolbar)
    Toolbar mTopToolbar;
    Unbinder unbinder;
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
        unbinder = ButterKnife.bind(this, mRootView);
        StatusBarUtil.immersiveStatusTest(getActivity());
        ((AppCompatActivity) getActivity()).setSupportActionBar(mTopToolbar);
        mTopToolbar.setPadding(0, StatusBarUtil.getStatusBarHeight(getContext()), 0, 0);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
