package com.libok.androidcode.util;

import android.util.Log;

/**
 * @author liboK
 * @date 2018/6/25 0025 3:19
 * @e-mail libolf@outlook.com
 * @description 快捷Log
 */
public class L {
    private static boolean DEBUG = true;

    public static void e(String tag, String message, Throwable tr) {
        if (DEBUG) {
            Log.e(tag, message, tr);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }
}
