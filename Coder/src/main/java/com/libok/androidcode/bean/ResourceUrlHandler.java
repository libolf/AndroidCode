package com.libok.androidcode.bean;

import android.util.Log;

import com.libok.androidcode.interf.IResourceUrlHandler;
import com.libok.androidcode.socket.HttpContext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author liboK  2018/09/11 下午 07:34
 */
public class ResourceUrlHandler implements IResourceUrlHandler {
    private static final String TAG = "ResourceUrlHandler";

    private String acceptPrefix = "/static/";

    @Override
    public boolean accept(String url) {
        return url.startsWith(acceptPrefix);
    }

    @Override
    public void handle(String url, HttpContext httpContext) {
        OutputStream outputStream = null;
        try {
            outputStream = httpContext.getSocket().getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println("HTTP/1.1 200 OK");
        writer.println();

        writer.println("from resource in assets handler");

        writer.flush();
        writer.close();
        Log.e(TAG, "handle: resource ok");
    }
}
