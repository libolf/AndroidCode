package com.libok.androidcode.socket;

import android.util.Log;

import com.libok.androidcode.bean.WebConfig;
import com.libok.androidcode.interf.IResourceUrlHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
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
    private Set<IResourceUrlHandler> mResourceUrlHandlers;

    public SimpleHttpServer(WebConfig webConfig) {
        mWebConfig = webConfig;
        mCachedThreadPool = Executors.newCachedThreadPool();
        mResourceUrlHandlers = new HashSet<>();
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

    public void registerResourceUrlHandler(IResourceUrlHandler handler) {
        mResourceUrlHandlers.add(handler);
    }

    private void onAcceptRemotePeer(Socket socket) {
        Log.e(TAG, "onAcceptRemotePeer: start");
        try {
//            socket.getOutputStream().write("hello".getBytes());
            HttpContext httpContext = new HttpContext();
            httpContext.setUnderlySocket(socket);
            InputStream inputStream = socket.getInputStream();
            String headerLine = StreamToolkit.readLine(inputStream);
            String resourceUrl = headerLine.split(" ")[1];
            Log.e(TAG, "onAcceptRemotePeer: headerLine = " + headerLine);
            Log.e(TAG, "onAcceptRemotePeer: resourceUrl = " + resourceUrl);
            while ((headerLine = StreamToolkit.readLine(inputStream)) != null) {
                if (headerLine.equals("\r\n")) {
                    break;
                }
                Log.e(TAG, "onAcceptRemotePeer: " + headerLine);
                String[] split = headerLine.split(":");
                httpContext.addRequestHeader(split[0], split[1]);
            }
            for (IResourceUrlHandler handler : mResourceUrlHandlers) {
                if (!handler.accept(resourceUrl)) {
                    continue;
                }
                handler.handle(resourceUrl, httpContext);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
