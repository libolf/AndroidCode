package com.libok.androidcode.interf;

import com.libok.androidcode.socket.HttpContext;

/**
 * @author liboK  2018/09/11 下午 07:30
 */
public interface IResourceUrlHandler {

    boolean accept(String url);

    void handle(String url, HttpContext httpContext);
}
