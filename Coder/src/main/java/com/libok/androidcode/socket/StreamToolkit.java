package com.libok.androidcode.socket;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author liboK  2018/08/21 下午 07:54
 * 解析HTTP请求头
 */
public class StreamToolkit {

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int c1 = 0;
        int c2 = 0;

        while (c2 != -1 && !(c1 == '\r' && c2 == '\n')) {
            c1 = c2;
            c2 = inputStream.read();
            stringBuilder.append((char) c2);
        }

        if (stringBuilder.length() == 0) {
            return null;
        }
        return stringBuilder.toString();
    }
}
