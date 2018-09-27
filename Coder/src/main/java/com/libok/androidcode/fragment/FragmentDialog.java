package com.libok.androidcode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.libok.androidcode.R;
import com.libok.androidcode.util.Constants;
import com.libok.androidcode.util.ToastUtils;

/**
 * @author liboK  2018/09/26 下午 04:49
 */
public class FragmentDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "FragmentDialog";

    private String mTitle;
    private String mMessage;

    public static FragmentDialog newInstance(String title, String message) {

        Bundle args = new Bundle();
        args.putString(Constants.FragmentConst.FRAGMENT_KEY_DIALOG_TITLE, title);
        args.putString(Constants.FragmentConst.FRAGMENT_KEY_DIALOG_MESSAGE, message);

        FragmentDialog fragment = new FragmentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = mMessage = null;

        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(Constants.FragmentConst.FRAGMENT_KEY_DIALOG_TITLE);
            mMessage = arguments.getString(Constants.FragmentConst.FRAGMENT_KEY_DIALOG_MESSAGE);
        }

        if (mTitle == null || mMessage == null) {
            throw new IllegalArgumentException("title or message is null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        TextView mTitleText = view.findViewById(R.id.fragment_dialog_title_text);
        TextView mMessageText = view.findViewById(R.id.fragment_dialog_message_text);
        Button mNegativeButton = view.findViewById(R.id.fragment_dialog_negative_button);
        Button mPositiveButton = view.findViewById(R.id.fragment_dialog_positive_button);

        mTitleText.setText(mTitle);
        mMessageText.setText(mMessage);

        mNegativeButton.setOnClickListener(this);
        mPositiveButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_dialog_negative_button:
                dismiss();
                break;
            case R.id.fragment_dialog_positive_button:
                ToastUtils.showToast(getActivity(), "确定", Toast.LENGTH_SHORT);
                dismiss();
                break;
        }
    }
}
