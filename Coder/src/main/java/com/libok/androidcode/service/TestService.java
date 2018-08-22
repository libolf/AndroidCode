package com.libok.androidcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author libok
 * @date 2018/6/25 00253:15
 * @e-mail libo@nebulaera.com
 * @description 测试Service
 */
public class TestService extends Service {
    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
