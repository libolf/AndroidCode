package com.libok.androidcode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.libok.androidcode.R;

import butterknife.OnClick;

/**
 * @author liboK  2018/08/28 下午 02:20
 */
public class FragmentTest1 extends Fragment implements View.OnClickListener{

    private static final String TAG = "FragmentTest1";

    private String mTitle;

    public static FragmentTest1 newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        FragmentTest1 fragment = new FragmentTest1();
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
        View view = inflater.inflate(R.layout.fragment_test1, container, false);
        Button button = view.findViewById(R.id.fragment_test1_skip);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_test1_skip) {
            Toast.makeText(getActivity(), "Skip", Toast.LENGTH_SHORT).show();
            FragmentActivity activity = getActivity();
            if (activity != null) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.add(FragmentTest2.newInstance("Two"), "TWO");
                beginTransaction.commit();
            }
        }
    }
}
