package com.libok.androidcode.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * @author liboK
 * @date 2018/6/25 0025 3:10
 * @e-mail libolf@outlook.com
 * @description APP信息类
 */
public class AppInfo implements Serializable{

    private static final long serialVersionUID = -3577602297393471988L;
    private String mAppLabel;
    private String mAppPackageName;
    private Drawable mAppIcon;
    private boolean mAppSelected;

    public AppInfo() {
    }

    public AppInfo(String appLabel, String appPackageName, Drawable appIcon, boolean appSelected) {
        mAppLabel = appLabel;
        mAppPackageName = appPackageName;
        mAppIcon = appIcon;
        mAppSelected = appSelected;
    }

    public String getAppLabel() {
        return mAppLabel;
    }

    public AppInfo setAppLabel(String appLabel) {
        mAppLabel = appLabel;
        return this;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public AppInfo setAppPackageName(String appPackageName) {
        mAppPackageName = appPackageName;
        return this;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public AppInfo setAppIcon(Drawable appIcon) {
        mAppIcon = appIcon;
        return this;
    }

    public boolean isAppSelected() {
        return mAppSelected;
    }

    public AppInfo setAppSelected(boolean appSelected) {
        mAppSelected = appSelected;
        return this;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "mAppLabel='" + mAppLabel + '\'' +
                ", mAppPackageName='" + mAppPackageName + '\'' +
                ", mAppSelected=" + mAppSelected +
                '}';
    }
}
