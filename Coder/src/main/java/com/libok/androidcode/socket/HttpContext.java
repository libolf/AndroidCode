package com.libok.androidcode.socket;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liboK  2018/08/22 下午 07:21
 */
public class HttpContext {

    private Socket mSocket;
    private Map<String, String> mRequestMap;

    public HttpContext() {
        mRequestMap = new HashMap<>();
    }

    public void setUnderlySocket(Socket socket) {
        mSocket = socket;
    }

    public Socket getSocket() {
        return mSocket;
    }

    public void addRequestHeader(String header, String value) {
        mRequestMap.put(header, value);
    }
}
