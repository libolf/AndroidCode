package com.libok.androidcode.core;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.libok.androidcode.activity.DialogActivity;

/**
 * @author liboK 2018/7/30 0030 9:44
 */
public class MyCrash implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "MyCrash";

    private static MyCrash INSTANCE = null;

    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private MyCrash() {
    }

    public static MyCrash getInstance() {
        if (INSTANCE == null) {
            synchronized (MyCrash.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyCrash();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e == null) {
            Log.e(TAG, "uncaughtException: throwable is null");
        }
        if (!handleException(e) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处
            mDefaultHandler.uncaughtException(t, e);
        } else {
            // 跳转到崩溃提示Activity
            Intent intent = new Intent(mContext, DialogActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("error", e);
            mContext.startActivity(intent);
            System.exit(1);// 关闭已奔溃的app进程
        }
//        new Thread(){
//            @Override
//            public void run() {
//                Looper.prepare();
//                mContext.startActivity(new Intent(mContext, DialogActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
////                createErrorDialog("error");
//                Looper.loop();
//            }
//        }.start();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//        Log.e(TAG, "uncaughtException: " + e.getMessage());
////        for (StackTraceElement element : e.getStackTrace()) {
////            Log.e(TAG, "uncaughtException: " + element.getMethodName());
////        }
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(stringWriter);
//        e.printStackTrace(printWriter);
//        printWriter.flush();
//        LineNumberReader reader = new LineNumberReader(new StringReader(stringWriter.toString()));
//        List<String> list = new ArrayList<>();
//        String line = null;
//        try {
//            while ((line = reader.readLine()) != null) {
//                list.add("\n" + line);
////                Log.e(TAG, "uncaughtException: " + line);
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
////        Log.e(TAG, list.toString());
////        createErrorDialog(list.toString());
//        System.exit(0);
    }

    private boolean handleException(Throwable throwable) {

        if (throwable == null) {
            return false;
        }
        LApplication.finishAllActivity();
        return true;
    }

    private void createErrorDialog(String message) {
        if (mContext == null) {
            Log.e(TAG, "createErrorDialog: context null");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("出错啦");
        builder.setMessage(message);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("反馈", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alertDialog.show();
    }
}
