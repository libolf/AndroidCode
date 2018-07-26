package com.libok.androidcode.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author liboK
 * @date 2018/6/25 0025 3:17
 * @e-mail libolf@outlook.com
 * @description 快捷Toast
 */
public class T {

    private static boolean DEBUG = true;

    public static void makeText(Context context, String message) {
        if (DEBUG) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
