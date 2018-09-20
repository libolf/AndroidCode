package com.libok.androidcode.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.Bundle;

import com.libok.androidcode.R;
import com.libok.androidcode.bean.ResourceUrlHandler;
import com.libok.androidcode.bean.UploadImageHandler;
import com.libok.androidcode.bean.WebConfig;
import com.libok.androidcode.core.LApplication;
import com.libok.androidcode.socket.SimpleHttpServer;

public class SocketActivity extends BaseActivity {

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
    protected int setContentViewId() {
        return R.layout.activity_socket;
    }

    @Override
    protected String setActivityTitle() {
        return "手机微服务器";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        WebConfig webConfig = new WebConfig(9635, 20);
        mSimpleHttpServer = new SimpleHttpServer(webConfig);
        mSimpleHttpServer.registerResourceUrlHandler(new ResourceUrlHandler());
        mSimpleHttpServer.registerResourceUrlHandler(new UploadImageHandler());
        mSimpleHttpServer.startAsync();
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

    @Override
    protected void onDestroy() {
//        unbindService(mServiceConnection);
        mSimpleHttpServer.stopAsync();
        super.onDestroy();
    }
}
