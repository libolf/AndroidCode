package com.libok.androidcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private static class MyMessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: ");
            if (msg.what == 1) {
                Log.e(TAG, "handleMessage: " + msg.getData().getString("data"));
            }
        }
    }

    private Messenger mMessenger = new Messenger(new MyMessengerHandler());

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mMessenger.getBinder();
    }
}
