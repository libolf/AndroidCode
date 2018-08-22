package com.libok.androidcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.libok.androidcode.R;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity {

    private static final String TAG = "DialogActivity";
    @BindView(R.id.crash_exit)
    Button mCrashExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        Log.e(TAG, "onCreate: start");
        Serializable error = getIntent().getSerializableExtra("error");
        if (error instanceof Throwable) {
            Throwable throwable = (Throwable) error;
//            StringWriter stringWriter = new StringWriter();
//            PrintWriter printWriter = new PrintWriter(stringWriter);
//            throwable.printStackTrace(printWriter);
//            printWriter.flush();
//            LineNumberReader reader = new LineNumberReader(new StringReader(stringWriter.toString()));
//            List<String> list = new ArrayList<>();
//            String line = null;
//            try {
//                while ((line = reader.readLine()) != null) {
//                    list.add("\n" + line);
//                    Log.e(TAG, "onCreate: " + line);
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String s = writer.toString();
        }
    }

    @OnClick(R.id.crash_exit)
    public void onViewClicked() {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
