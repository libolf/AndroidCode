package com.libok.androidcode.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.libok.androidcode.R;
import com.libok.androidcode.activity.HomeActivity;
import com.libok.androidcode.activity.IntentActivity;

/**
 * @author liboK  2018-09-18 0018 下午 06:34
 */
public class BaskTaskService extends Service {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Runnable mNotificationRunnable = new Runnable() {
        @Override
        public void run() {
            sendNotification();
        }
    };

    public BaskTaskService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.postDelayed(mNotificationRunnable, 3000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void sendNotification() {

        Intent intent = new Intent(this, IntentActivity.class);
        Intent intent1 = new Intent(this, HomeActivity.class);

//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        PendingIntent pendingIntent1 = PendingIntent.getActivities(this, 12, new Intent[]{intent1, intent}, 0);

//        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
//        taskStackBuilder.addParentStack(GifActivity.class);
//        taskStackBuilder.addNextIntent(intent);
////        taskStackBuilder.addNextIntent(intent1);
//        PendingIntent pendingIntent1 = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("Title");
        builder.setContentText("Message");
        builder.setAutoCancel(true);
//        builder.setContentIntent(pendingIntent1);
        builder.setFullScreenIntent(pendingIntent1, true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(213, builder.build());
    }
}
