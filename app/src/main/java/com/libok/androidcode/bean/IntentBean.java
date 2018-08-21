package com.libok.androidcode.bean;

import java.io.Serializable;

/**
 * @author liboK  2018/08/21 上午 11:09
 * 可启动的Intent
 */
public class IntentBean{
    private String mPackageName;
    private String mClassName;

    public IntentBean(String packageName, String className) {
        mPackageName = packageName;
        mClassName = className;
    }

    public String getPackageName() {
        return mPackageName;
    }

//    public IntentBean setPackageName(String packageName) {
//        mPackageName = packageName;
//        return this;
//    }

    public String getClassName() {
        return mClassName;
    }

//    public IntentBean setClassName(String className) {
//        mClassName = className;
//        return this;
//    }

    @Override
    public String toString() {
        return "IntentBean{" +
                "mPackageName='" + mPackageName + '\'' +
                ", mClassName='" + mClassName + '\'' +
                '}';
    }
}
