package com.libok.androidcode.model;

import java.util.List;

/**
 * @author liboK
 * @date 2018/7/26 0026 5:10
 * @e-mail libolf@outlook.com
 * @description
 */
public class AppLabelBean {
    private String[] mLabelSplite;
    private String mPackageName;
    private String mClassName;
    private String mCurrentName;
    private List<String> mChildLabel;

    public AppLabelBean(String[] labelSplite, String packageName, String className) {
        mLabelSplite = labelSplite;
        mPackageName = packageName;
        mClassName = className;
    }

    public List<String> getChildLabel() {
        return mChildLabel;
    }

    public AppLabelBean setChildLabel(List<String> childLabel) {
        mChildLabel = childLabel;
        return this;
    }

    public String getCurrentName() {
        return mCurrentName;
    }

    public AppLabelBean setCurrentName(String currentName) {
        mCurrentName = currentName;
        return this;
    }

    public String[] getLabelSplite() {
        return mLabelSplite;
    }

    public AppLabelBean setLabelSplite(String[] labelSplite) {
        mLabelSplite = labelSplite;
        return this;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public AppLabelBean setPackageName(String packageName) {
        mPackageName = packageName;
        return this;
    }

    public String getClassName() {
        return mClassName;
    }

    public AppLabelBean setClassName(String className) {
        mClassName = className;
        return this;
    }

    @Override
    public String toString() {
        return "AppLabelBean{" +
                "mPackageName='" + mPackageName + '\'' +
                ", mClassName='" + mClassName + '\'' +
                '}';
    }
}
