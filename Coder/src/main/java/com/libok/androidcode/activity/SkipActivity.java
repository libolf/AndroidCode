package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.libok.androidcode.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SkipActivity extends AppCompatActivity {

    private static final String TAG = "SkipActivity";
    @BindView(R.id.skip_text)
    TextView mSkipText;
    @BindView(R.id.skip_text_change)
    Button mSkipTextChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip);
        ButterKnife.bind(this);
        Log.e(TAG, "onCreate: ");
//        Book book = getIntent().getParcelableExtra("book");
//        Log.e(TAG, "onCreate: " + book.toString());
//        mSkipText.setText(book.toString());
        String s = getString(R.string.skip_test_string, 12.0d, 24.0d);
        Log.e(TAG, "onCreate: " +s);
    }

    @OnClick(R.id.skip_text_change)
    public void onViewClicked() {
//        CollapsingTextHelper
//        try {
//            Class cl = Class.forName("android.support.design.widget.CollapsingTextHelper");
//            CollapsingTextHelper collapsingTextHelper = cl.newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
