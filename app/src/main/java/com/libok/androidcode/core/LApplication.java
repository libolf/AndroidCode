package com.libok.androidcode.core;

import android.app.Activity;
import android.app.Application;

import com.libok.androidcode.util.AndroidLogger;
import com.libok.androidcode.util.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liboK
 * @date 2018/7/30 0030 9:58
 * @e-mail libolf@outlook.com
 * @description
 */
public class LApplication extends Application {

    private static List<Activity> mActivityList;

    @Override
    public void onCreate() {
        super.onCreate();
//        LoggerFactory.setLoggerImpl(new AndroidLogger(this));
//        MyCrash.getInstance().init(this);
        mActivityList = new ArrayList<>();
    }

    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public static void finishActivity() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
    }
}
