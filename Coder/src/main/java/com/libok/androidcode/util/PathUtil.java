package com.libok.androidcode.util;

import android.content.Context;
import android.os.Environment;

/**
 * @author liboK  2018/07/31 11:09
 */
public class PathUtil {

    /**
     * 返回data/data/<PackageName>下的Files文件夹的路径
     * @param context
     * @return
     */
    public static String getFilesDirPath(Context context) {
        return context.getFilesDir().getPath();
    }

    /**
     * 返回data/data/<PackageName>下的Cache文件夹的路径
     * @param context
     * @return
     */
    public static String getCacheDirPath(Context context) {
        return context.getCacheDir().getPath();
    }

    /**
     * @return /data
     */
    public static String getDataDirPath() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * @return /data/cache
     */
    public static String getDownloadCacheDirPath() {
        return Environment.getDownloadCacheDirectory().getPath();
    }

    /**
     * @return System根目录 /system read only
     */
    public static String getRootDirPath() {
        return Environment.getRootDirectory().getPath();
    }

    /**
     * @return 获取外部存储的路径/storage/emulated/0
     */
    public static String getExternalDirPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }
}
