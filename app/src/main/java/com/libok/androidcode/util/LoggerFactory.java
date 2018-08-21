package com.libok.androidcode.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liboK
 * @date 2018/7/31 0031 10:16
 * @e-mail libolf@outlook.com
 * @description
 */
public class LoggerFactory {
    private static Map<String, ILogger> loggerMap = new HashMap();
    public static final boolean LOG = false;
    protected static ILogger sLogger;

    public LoggerFactory() {
    }

    public static void setLoggerImpl(ILogger logger) {
        sLogger = logger;
    }

    public static ILogger getLogger(Class<?> c) {
        String name = c.getSimpleName();
        ILogger logger = loggerMap.get(name);
        if (logger == null) {
            logger = new DefaultLogger(name);
            loggerMap.put(name, logger);
        }

        return logger;
    }
}
