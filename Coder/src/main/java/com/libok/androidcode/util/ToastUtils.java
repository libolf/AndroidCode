package com.libok.androidcode.util;

import android.app.ActivityManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.libok.androidcode.R;

import java.util.List;

/**
 * @author liboK  2018/09/04 上午 10:31
 */
public class ToastUtils {

    public static boolean sIsAllowShow = true;
    private static Toast sToast = null;
    private static TextView sToastMessage = null;

    public static void showToast(Context context, String message, int duration) {
//        Log.e("libo", "showToast: " + getTopActivity(context));

        if (message.contains("任务") && !getTopActivity(context).contains("HomeActivity")) {
            return;
        }

        if (sIsAllowShow) {
            Toast toast = new Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.toast_universal, null);
            TextView messageText = view.findViewById(R.id.toast_universal_message_text);
            messageText.setText(message);
            toast.setDuration(duration);
            toast.setView(view);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private static String getTopActivity(Context context) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity).toString();
        } else
            return null;
    }

}
