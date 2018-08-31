package com.libok.androidcode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.bean.AppInfo;
import com.libok.androidcode.util.L;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.main_button_statusbar)
    Button mMainButtonStatusbar;
    @BindView(R.id.main_button_animation)
    Button mMainButtonAnimation;
    @BindView(R.id.main_button_gif)
    Button mMainButtonGif;
    @BindView(R.id.main_button_mvp)
    Button mMainButtonMvp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        File file = new File(getFilesDir().getAbsolutePath() + File.separator + "object_save.txt");
//        readObject(file);
//        writeObject(file);
//        XiaomiUpdateAgent.update(this, true);
//        XiaomiUpdateAgent.setUpdateAutoPopup(true);
    }

    private void writeObject(File file) {
        AppInfo appInfo = new AppInfo("微信", "com.tt.mm", null, false);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(appInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readObject(File file) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            if (!file.exists()) {
                return;
            }
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            AppInfo info = (AppInfo) objectInputStream.readObject();
            L.e(TAG, "onCreate: " + info.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.main_button_statusbar,
            R.id.main_button_animation,
            R.id.main_button_gif,
            R.id.main_button_mvp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_button_statusbar:
                startActivity(new Intent(this, StatusBarActivity.class));
                break;
            case R.id.main_button_animation:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case R.id.main_button_gif:
                startActivity(new Intent(this, GifActivity.class));
                break;
            case R.id.main_button_mvp:
                startActivity(new Intent(this, MVPActivity.class));
                break;
        }
    }
}
