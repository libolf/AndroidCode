package com.libok.androidcode.util;

import android.util.Log;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author liboK
 * @date 2018/7/31 0031 10:16
 * @e-mail libolf@outlook.com
 * @description
 */
public class DefaultLogger implements ILogger {
    private final String tag;

    public DefaultLogger(String name) {
        this.tag = name;
    }

    public void debug(String v, Object... param) {
        ILogger logger = LoggerFactory.sLogger;
        if (logger == null) {
            Log.e("libo", "debug: null");
        }
        String msg = this.format(this.tag, v, param);
        if (logger != null) {
            Log.e("libo", "debug: " + param);
            logger.debug(msg);
        } else {
            System.out.println(new Date() + ":" + msg);
        }

    }

    public void info(String v, Object... param) {
        ILogger logger = LoggerFactory.sLogger;
        String msg = this.format(this.tag, v, param);
        if (logger != null) {
            logger.info(msg);
        } else {
            System.out.println(new Date() + ":" + msg);
        }

    }

    private String format(String tag, String v, Object... param) {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("[").append(tag).append("][");
        logBuilder.append(Thread.currentThread().getName()).append("]");
        if (param != null && param.length > 0) {
            v = v.replaceAll(Pattern.quote("{}"), "%s").replaceAll("\r", "").replaceAll("\n", "");
            logBuilder.append(String.format(v, param));
        } else {
            logBuilder.append(v);
        }

        if (logBuilder.length() > 512) {
            logBuilder.delete(512, logBuilder.length());
        }

        return logBuilder.toString();
    }

    public void error(String v, Throwable throwable) {
        ILogger logger = LoggerFactory.sLogger;
        String msg = this.format(this.tag, v);
        if (logger != null) {
            if (throwable != null) {
                logger.error(msg, throwable);
            } else {
                logger.error(msg);
            }
        } else {
            System.out.println(new Date() + ":" + msg);
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }

    }

    public void error(String v) {
        this.error(v, null);
    }
}
