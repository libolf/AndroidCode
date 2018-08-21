package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.libok.androidcode.R;
import com.libok.androidcode.aidl.Book;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkipActivity extends AppCompatActivity {

    private static final String TAG = "SkipActivity";
    @BindView(R.id.skip_text)
    TextView mSkipText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip);
        ButterKnife.bind(this);
        Log.e(TAG, "onCreate: ");
        Book book = getIntent().getParcelableExtra("book");
        Log.e(TAG, "onCreate: " + book.toString());
        mSkipText.setText(book.toString());
    }
}
