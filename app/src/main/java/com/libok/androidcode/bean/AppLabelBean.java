package com.libok.androidcode.bean;

import java.util.List;

/**
 * @author liboK 2018/7/26 下午 5:10
 * AppLabel信息类
 */
public class AppLabelBean<T> {

    /**
     * 当前节点的保存数据
     */
    private T mData;
    /**
     * 当前节点标识
     */
    private String mTag;
    /**
     * 根节点到当前节点路径
     */
    private String mPath;
    /**
     * 当前节点的父节点，如果当前是根节点那么此项为null
     */
    private AppLabelBean<T> mParent;
    /**
     * 当前节点的子节点
     */
    private List<AppLabelBean<T>> mNext;

    /**
     * 当前节点的index值
     */
    private int mIndex;

    /**
     * path分割
     */
    private String[] mPathSplite;

    public AppLabelBean(String tag) {
        mTag = tag;
    }

    public AppLabelBean(T data, String tag, String path, int index, AppLabelBean<T> parent) {
        this.mData = data;
        this.mTag = tag;
        this.mPath = path;
        this.mIndex = index;
        this.mParent = parent;
        mNext = null;
        if (path != null && path.length() > 0) {
            mPathSplite = path.split("/");
        }
    }

    public T getData() {
        return mData;
    }

    public AppLabelBean<T> setData(T data) {
        mData = data;
        return this;
    }

    public String getTag() {
        return mTag;
    }

    public AppLabelBean<T> setTag(String tag) {
        mTag = tag;
        return this;
    }

//    public String getPath() {
//        return mPath;
//    }

//    public AppLabelBean<T> setPath(String path) {
//        mPath = path;
//        return this;
//    }

    public AppLabelBean<T> getParent() {
        return mParent;
    }

    public AppLabelBean<T> setParent(AppLabelBean<T> parent) {
        mParent = parent;
        return this;
    }

    public List<AppLabelBean<T>> getNext() {
        return mNext;
    }

    public void setNext(List<AppLabelBean<T>> next) {
        mNext = next;
    }

    public int getIndex() {
        return mIndex;
    }

//    public AppLabelBean<T> setIndex(int index) {
//        mIndex = index;
//        return this;
//    }

    public String[] getPathSplite() {
        return mPathSplite;
    }

    @Override
    public String toString() {
        return "AppLabelBean{" +
                "tag='" + mTag + '\'' +
                ", path='" + mPath + '\'' +
                ", childSize= " + (mNext == null ? -1 : mNext.size()) +
                ", index= " + mIndex +
                ", parent= " + (mParent == null ? "First" : mParent.getTag()) +
                ", data= " + (mData == null ? "data is null" : mData.toString()) +
                '}';
    }
}
