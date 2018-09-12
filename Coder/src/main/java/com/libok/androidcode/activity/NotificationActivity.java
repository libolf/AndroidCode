package com.libok.androidcode.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.libok.androidcode.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 6382;

    @BindView(R.id.notification_show_button)
    Button mNotificationShowButton;
    @BindView(R.id.notification_show_hint_button)
    Button mNotificationShowHintButton;
    @BindView(R.id.notification_cancel_button)
    Button mNotificationCancelButton;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.notification_show_button, R.id.notification_show_hint_button, R.id.notification_cancel_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notification_show_button:
//                showNotification();
//                showNotification1();
                showSimpleNotification();
                break;
            case R.id.notification_show_hint_button:
                showNotificationAndHint();
                break;
            case R.id.notification_cancel_button:
                cancelNotification();
                break;
        }
    }

    private void cancelNotification() {

    }

    private void showNotificationAndHint() {

    }

    public void showSimpleNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("OJBK");
        builder.setContentText("你好啊");
        builder.setContentTitle("标题");

        mNotificationManager.notify(879, builder.build());
    }

    public void showNotification1() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.notification_small_view);
        RemoteViews remoteViews_large=new RemoteViews(getPackageName(),R.layout.notification_big_view);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setOngoing(true)
                .setTicker("music is playing");
        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.bigContentView=remoteViews_large;
        }
        notification.contentView=remoteViews;

        mNotificationManager.notify(1,notification);
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
