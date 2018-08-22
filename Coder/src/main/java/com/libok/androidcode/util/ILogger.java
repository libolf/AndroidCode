package com.libok.androidcode.util;

import android.support.annotation.NonNull;

/**
 * @author liboK 2018/7/31 10:15
 */
public interface ILogger {
    void debug(@NonNull String var1, @NonNull Object... var2);

    void info(@NonNull String var1, @NonNull Object... var2);

    void error(@NonNull String var1,@NonNull Throwable var2);

    void error(@NonNull String var1);
}
