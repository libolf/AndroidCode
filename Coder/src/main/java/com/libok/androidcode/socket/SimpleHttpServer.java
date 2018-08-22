package com.libok.androidcode.socket;

import android.util.Log;

import com.libok.androidcode.bean.WebConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liboK  2018/08/21 下午 07:22
 * 微服务器
 */
public class SimpleHttpServer {

    private static final String TAG = "SimpleHttpServer";

    private final ExecutorService mCachedThreadPool;
    private boolean mIsEnable;
    private WebConfig mWebConfig;
    private ServerSocket mServerSocket;

    public SimpleHttpServer(WebConfig webConfig) {
        mWebConfig = webConfig;
        mCachedThreadPool = Executors.newCachedThreadPool();
    }

    public void startAsync() {
        mIsEnable = true;
        boolean isEnable = mIsEnable;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProcSync();
            }
        }).start();
    }

    public void stopAsync() {
        if (!mIsEnable) {
            return;
        }
        mIsEnable = false;
        try {
            mServerSocket.close();
            mServerSocket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doProcSync() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(mWebConfig.getPort());
            mServerSocket = new ServerSocket();
            mServerSocket.bind(socketAddress);
            while (mIsEnable) {
                final Socket socket = mServerSocket.accept();
                mCachedThreadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: connect " + socket.getRemoteSocketAddress().toString());
                        onAcceptRemotePeer(socket);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAcceptRemotePeer(Socket socket) {
        try {
//            socket.getOutputStream().write("hello".getBytes());
            InputStream inputStream = socket.getInputStream();
            String headerLine = null;
            while ((headerLine = StreamToolkit.readLine(inputStream)) != null) {
                if (headerLine.equals("\r\n")) {
                    break;
                }
                Log.e(TAG, "onAcceptRemotePeer: " + headerLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
