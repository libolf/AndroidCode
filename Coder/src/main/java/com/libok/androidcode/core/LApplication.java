package com.libok.androidcode.core;

import android.app.Activity;
import android.app.Application;

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
        //内存泄漏检测
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }

    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    public static void finishAllActivity() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
    }
}
