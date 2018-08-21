package com.libok.androidcode.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author liboK  2018/07/31 11:00
 */
public class FileUtil {

    /**
     * 创建新文件，默认删除旧重名文件
     * @param path
     * @return
     */
    public static boolean createFile(String path) {
        return createFile(path, true);
    }

    /**
     * 创建新文件
     * @param path
     * @param createANew 是否删除旧重名文件
     * @return
     */
    public static boolean createFile(String path, boolean createANew) {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return false;
        }
        if (parentFile.exists()) {
            if (!parentFile.canWrite()) {
                parentFile.setWritable(true);
            }
        } else {
            createDir(parentFile);
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                if (createANew) {
                    file.delete();
                    file.createNewFile();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 创建文件夹
     *
     * @param dir
     * @return
     */
    public static boolean createDir(File dir) {
        File parentFile = dir.getParentFile();
        if (parentFile == null) {
            return false;
        }
        if (parentFile.exists()) {
            try {
                if (!parentFile.canWrite()) {
                    parentFile.setWritable(true, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            createDir(parentFile);
        }
        if (!dir.exists()) {
            return dir.mkdir();
        } else {
            return true;
        }
    }

    /**
     * 拷贝文件
     * @param srcStream
     * @param destPath
     * @throws IOException
     */
    public static void copyFileTo(InputStream srcStream, String destPath) throws IOException {
        File desFile = new File(destPath);
        createDir(desFile.getParentFile());
        OutputStream os = new FileOutputStream(desFile);
        try {
            int realLength = 0;
            byte[] buffer = new byte[10240];
            while ((realLength = srcStream.read(buffer)) != -1) {
                os.write(buffer, 0, realLength);
            }
            buffer = null;
        } finally {
            os.close();
            srcStream.close();
        }
    }

}
