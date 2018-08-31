package com.libok.androidcode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.libok.androidcode.R;

/**
 * @author liboK  2018/08/28 下午 02:21
 */
public class FragmentTest2 extends Fragment {

    private static final String TAG = "FragmentTest2";

    private String mTitle;

    public static FragmentTest2 newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        FragmentTest2 fragment = new FragmentTest2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString("title");
            Log.e(TAG, "onCreate: " + mTitle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test2, null);
        return view;
    }
}
