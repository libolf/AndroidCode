package com.libok.androidcode.util;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.libok.androidcode.util.Constants.UtilConst;

/**
 * @author liboK  2018/09/13 下午 01:45
 * 减少重复创建线程的资源消耗
 */
public class ThreadPoolUtils {

    private static final String TAG = "ThreadPoolUtils";

    private static ThreadPoolUtils sThreadPoolUtils = null;
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolUtils() {
        mThreadPoolExecutor = new ThreadPoolExecutor(UtilConst.THREAD_POOL_CORE_SIZE, UtilConst.THREAD_POOL_MAXIMUM_SIZE, UtilConst.THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(UtilConst.THREAD_POOL_QUEUE_SIZE),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        Log.e(TAG, "Task is full " + r);
                        if (!executor.isShutdown()) {
                            r.run();
                        }
                    }
                });
    }

    public static ThreadPoolUtils getInstance() {
        if (sThreadPoolUtils == null) {
            synchronized (ThreadPoolUtils.class) {
                if (sThreadPoolUtils == null) {
                    sThreadPoolUtils = new ThreadPoolUtils();
                }
            }
        }
        return sThreadPoolUtils;
    }

    public void executeRunnable(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    private String showThreadPoolState() {

        return "running:" +
                mThreadPoolExecutor.getActiveCount() +
                "\n" +
                "core:" +
                mThreadPoolExecutor.getCorePoolSize() +
                "\n" +
                "all:" +
                mThreadPoolExecutor.getPoolSize() +
                "\n" +
                "queue:" +
                mThreadPoolExecutor.getQueue().size() +
                "\n" +
                "completed:" +
                mThreadPoolExecutor.getCompletedTaskCount() +
                "\n";
    }

    @Override
    public String toString() {
        return showThreadPoolState();
    }
}
