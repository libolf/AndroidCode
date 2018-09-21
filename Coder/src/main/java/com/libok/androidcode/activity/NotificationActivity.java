package com.libok.androidcode.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.libok.androidcode.R;
import com.libok.androidcode.core.LApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {

    private static final int NOTIFICATION_ID = 6382;

    @BindView(R.id.notification_show_button)
    Button mNotificationShowButton;
    @BindView(R.id.notification_show_hint_button)
    Button mNotificationShowHintButton;
    @BindView(R.id.notification_cancel_button)
    Button mNotificationCancelButton;
    private NotificationManager mNotificationManager;

    private int progress = 0;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                progress += 5;
                if (progress <= 100) {
                    showProgressNotification(progress);
                    sendEmptyMessageDelayed(1, 2000);
                } else {
                    removeMessages(1);
                }
            }
        }
    };

    @Override
    protected int setContentViewId() {
        return R.layout.activity_notification;
    }

    @Override
    protected String setActivityTitle() {
        return "通知栏";
    }

    @Override
    protected int setActivityAnim() {
        return 0;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.notification_show_button, R.id.notification_show_hint_button, R.id.notification_cancel_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notification_show_button:
//                showNotification();
//                showNotification1();
//                showSimpleNotification();
                mHandler.sendEmptyMessage(1);
                break;
            case R.id.notification_show_hint_button:
                showNotificationAndHint();
                break;
            case R.id.notification_cancel_button:
                cancelNotification();
                break;
        }
    }

    /**
     * 带进度的下载通知
     *
     * @param progress 进度
     */
    private void showProgressNotification(int progress) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle("更新最新版本");
        builder.setContentText("下载进度:" + progress);
        builder.setProgress(100, progress, false);
        mNotificationManager.notify(89, builder.build());
    }

    private void cancelNotification() {

    }

    private void showNotificationAndHint() {

    }

    public void showSimpleNotification() {
        final Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("OJBK");
        builder.setContentText("你好啊");
        builder.setContentTitle("标题");
        builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNotificationManager.notify(879, builder.build());
            }
        }, 5000);
    }

    public void showNotification1() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.notification_small_view);
        RemoteViews remoteViews_large = new RemoteViews(getPackageName(), R.layout.notification_big_view);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setOngoing(true)
                .setTicker("music is playing");
        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView = remoteViews_large;
        }
        notification.contentView = remoteViews;

        mNotificationManager.notify(1, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showNotification() {
        RemoteViews smallView = new RemoteViews(getPackageName(), R.layout.notification_small_view);
        RemoteViews bigView = new RemoteViews(getPackageName(), R.layout.notification_big_view);


//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 12, new Intent(this, NotificationActivity.class), 0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("hello");
        builder.setOngoing(true);
        builder.setContent(smallView);
        builder.setCustomBigContentView(bigView);
        builder.setPriority(Notification.PRIORITY_MAX);
//        builder.setContentIntent(pendingIntent);

//        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this);
//        builder1.setSmallIcon(R.drawable.ic_launcher);
//        builder1.setTicker("Hello");
//        builder1.setOngoing(true);
//        Notification notification = builder1.build();
//        notification.bigContentView = bigView;
//        notification.contentView = smallView;
//        notification.priority = NotificationCompat.PRIORITY_MAX;

        mNotificationManager.notify(321, builder.build());
    }
}
