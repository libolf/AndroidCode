package com.libok.androidcode.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;

import com.libok.androidcode.R;
import com.libok.androidcode.aidl.Book;
import com.libok.androidcode.core.LApplication;
import com.libok.androidcode.util.ILogger;
import com.libok.androidcode.util.LoggerFactory;
import com.libok.androidcode.util.PathUtil;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueueWriteActivity extends BaseActivity {

    private static final ILogger log = LoggerFactory.getLogger(QueueWriteActivity.class);
    private static final String TAG = "QueueWriteActivity";
    @BindView(R.id.queue_write_stop)
    Button mQueueWriteStop;

    private Random mRandom;
    private boolean isRunning = true;

    private String[] permission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected int setContentViewId() {
        return R.layout.activity_queue_write;
    }

    @Override
    protected String setActivityTitle() {
        return "日志记录";
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
// getPath  相对路径
        // getAbsolute  绝对路径
//        log.error("path = " + logDirectory.getPath() + "absolutePath = " + logDirectory.getAbsolutePath());

        mRandom = new Random();
//        ThreadLocal
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();

//        new LogThread("AAAAAAAAA", null).start();
//        new LogThread("BBBBBBBBB", null).start();
//        new LogThread("CCCCCCCCC", null).start();
//        new LogThread("DDDDDDDDD", null).start();
//        new LogThread("EEEEEEEEE", null).start();
//        new LogThread("FFFFFFFFF", null).start();
//        new LogThread("GGGGGGGGG", null).start();
//        new LogThread("HHHHHHHHH", null).start();

//        log.error(PathUtil.getCacheDirPath(this));
//        log.error(PathUtil.getFilesDirPath(this));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permission, 123);
        } else {
            testCreateFiles();
        }
//        startActivity(new Intent(this, SkipActivity.class));
//        finish();
    }

    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void addActivityToList() {
        LApplication.addActivity(this);
    }

    @Override
    protected void removeActivityForList() {
        LApplication.removeActivity(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123) {
            int i;
            for (i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    break;
                }
            }
            if (i == grantResults.length) {
                testCreateFiles();
            }
        }
    }

    private void testCreateFiles() {
//        log.error(PathUtil.getDataDirPath());
//        File file1 = new File(PathUtil.getDataDirPath() + File.separator + "data.libo");
//        if (!file1.exists()) {
//            try {
//                file1.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        log.error(PathUtil.getDownloadCacheDirPath());
//        File file2 = new File(PathUtil.getDownloadCacheDirPath() + File.separator + "downloadcache.libo");
//        if (!file2.exists()) {
//            try {
//                file2.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        log.error(PathUtil.getExternalDirPath());
        File file3 = new File(PathUtil.getExternalDirPath() + File.separator + "external.libo");
        if (!file3.exists()) {
            try {
                file3.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        log.error(PathUtil.getRootDirPath());
//        File file4 = new File(PathUtil.getRootDirPath() + File.separator + "root.libo");
//        if (!file4.exists()) {
//            try {
//                file4.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @OnClick(R.id.queue_write_stop)
    public void onViewClicked() {
//        isRunning = false;
//        startActivity(new Intent(this, QueueWriteActivity.class).putExtra("header", "come on"));
        Book book = new Book("qwe", "asd", 12);
        startActivity(new Intent(this, SkipActivity.class).putExtra("book", book));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        log.error("newIntent = " + intent.getStringExtra("header"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    private class LogThread extends Thread {

        private String tag;
        private int count = 0;
//        private BufferedWriter mBufferedWriter;

        public LogThread(String tag, File file) {
            this.tag = tag;
//            try {
//                mBufferedWriter = new BufferedWriter(new FileWriter(file));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void run() {
            while (isRunning) {
//                try {
//                    mBufferedWriter.write("new Thread {}" + currentThread().getId() + " 蔡当局为金门做什么？台媒体人预言：金门高粱将成大陆品牌 中国台湾网7月31日讯 据台湾“中时电子报”报道，为了表达对东亚青运停办的不满，台当局陆委会日前表示“时机不宜”，希望金门县府推迟金厦引水典礼，但目前通水则不受...[详情] " + (count++));
//                    mBufferedWriter.newLine();
//                    mBufferedWriter.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                log.error(tag + "new Thread {}" + currentThread().getId() + " 蔡当局为金门做什么？台媒体人预言：金门高粱将成大陆品牌 中国台湾网7月31日讯 据台湾“中时电子报”报道，为了表达对东亚青运停办的不满，台当局陆委会日前表示“时机不宜”，希望金门县府推迟金厦引水典礼，但目前通水则不受...[详情] " + (count++));
                try {
                    sleep(mRandom.nextInt(500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
