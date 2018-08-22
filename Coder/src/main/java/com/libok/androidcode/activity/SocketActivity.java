package com.libok.androidcode.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.libok.androidcode.R;
import com.libok.androidcode.bean.WebConfig;
import com.libok.androidcode.service.MessengerService;
import com.libok.androidcode.socket.SimpleHttpServer;

public class SocketActivity extends AppCompatActivity {

    private static final String TAG = "SocketActivity";

    private Messenger mMessenger;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            Message message = Message.obtain(null, 1);
            Bundle data = new Bundle();
            data.putString("data", "hello messenger");
            message.setData(data);
            try {
                mMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private SimpleHttpServer mSimpleHttpServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
//        bindService(new Intent(this, MessengerService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

        WebConfig webConfig = new WebConfig(9635, 20);
        mSimpleHttpServer = new SimpleHttpServer(webConfig);
        mSimpleHttpServer.startAsync();
    }

    @Override
    protected void onDestroy() {
//        unbindService(mServiceConnection);
        mSimpleHttpServer.stopAsync();
        super.onDestroy();
    }
}
