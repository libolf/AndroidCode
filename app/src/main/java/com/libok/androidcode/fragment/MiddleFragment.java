package com.libok.androidcode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libok.androidcode.R;

/**
 * @author liboK
 * @date 2018/6/26 0026 10:31
 * @e-mail libolf@outlook.com
 * @description
 */
public class MiddleFragment extends Fragment {

    private static final String TAG = "MiddleFragment";
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
//        StatusBarUtil.immerseStatusBar(getActivity());
        return mRootView;
    }
}
