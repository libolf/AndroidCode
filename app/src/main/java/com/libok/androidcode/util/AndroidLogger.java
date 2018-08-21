package com.libok.androidcode.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.Arrays;

/**
 * @author liboK 2018/7/31 0031 10:46
 */
public class AndroidLogger implements ILogger {
    private static final String TAG = "AndroidLogger";

    private final File mLogFile;
    private BufferedWriter mBufferedWriter;

    public AndroidLogger(@NonNull Context context) {
//        File logDirectory = new File(context.getFilesDir().getPath() + File.separator + "log");

        File logDirectory = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "dht_log");
        Log.e(TAG, "AndroidLogger: " + Environment.getExternalStorageState());
        if (!logDirectory.exists()) {
            logDirectory.mkdir();
        } /*else {
            logDirectory.delete();
            logDirectory.mkdir();
        }*/


        File file = new File(logDirectory.getPath() + File.separator + new Date(System.currentTimeMillis()) + ".log");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] dirList = logDirectory.list();//{"2018-10-31.log", "2018-07-31.log", "2018-07-22.log", "2018-07-10.log", "2018-06-24.log", "2018-08-31.log", "2018-07-31.log", "2018-07-31.log"};
        if (dirList.length > 2) {
            Arrays.sort(dirList);
            File deleteFile = new File(logDirectory.getPath() + File.separator + dirList[0]);
            boolean deleted = deleteFile.delete();
            Log.e("libo", "AndroidLogger: deleted = " + deleted);
        }

        Log.e("libo", "AndroidLogger: " + "log dir size = " + dirList.length + dirList.toString());
        for (String s : dirList) {
            Log.e("libo", "AndroidLogger: " + s);
        }
//        Log.e("libo", "AndroidLogger: " + (dirList[1].compareTo(dirList[0])));

        mLogFile = new File(logDirectory.getPath() + File.separator + new Date(System.currentTimeMillis()) + ".log");
//        BufferedOutputStream bufferedOutputStream = null;
        mBufferedWriter = null;
//        FileOutputStream fileOutputStream = null;
        FileWriter fileWriter = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
//            fileOutputStream = new FileOutputStream(mLogFile);
//            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            fileWriter = new FileWriter(mLogFile, true);
            mBufferedWriter = new BufferedWriter(fileWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void debug(@NonNull String v, @NonNull Object... param) {
        Log.d("default", v);
    }

    @Override
    public void info(@NonNull String v, @NonNull Object... param) {
        Log.i("default", v);
    }
    @Override
    public void error(@NonNull String v, @NonNull Throwable throwable) {
        try {
            // BufferedWriter是线程同步的，多个线程同时写数据到文件也是可行的
            mBufferedWriter.write(v);
            mBufferedWriter.newLine();
            mBufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(throwable!=null) {
            Log.e("default", v, throwable);
        }else {
            Log.e("default",v);
        }
    }

    @Override
    public void error(@NonNull String v) {
        error(v,null);
    }
}
