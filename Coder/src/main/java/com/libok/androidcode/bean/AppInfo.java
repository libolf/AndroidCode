package com.libok.androidcode.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * @author liboK 2018/6/25 下午 3:10
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

    public void setAppLabel(String appLabel) {
        mAppLabel = appLabel;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        mAppPackageName = appPackageName;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        mAppIcon = appIcon;
    }

    public boolean isAppSelected() {
        return mAppSelected;
    }

    public void setAppSelected(boolean appSelected) {
        mAppSelected = appSelected;
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
