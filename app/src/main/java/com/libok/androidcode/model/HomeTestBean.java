package com.libok.androidcode.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

/**
 * @author liboK
 * @date 2018/7/26 0026 2:50
 * @e-mail libolf@outlook.com
 * @description
 */
public class HomeTestBean {
    private String mString;
    private int mId;

    public HomeTestBean(String string, @DrawableRes int id) {
        mString = string;
        mId = id;
    }

    public String getString() {
        return mString;
    }

    public HomeTestBean setString(String string) {
        mString = string;
        return this;
    }

    public int getId() {
        return mId;
    }

    public HomeTestBean setId(int id) {
        mId = id;
        return this;
    }
}
