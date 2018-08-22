package com.libok.androidcode.bean;

/**
 * @author liboK  2018/08/21 下午 07:25
 * 微服务器配置类
 */
public class WebConfig {
    /**
     * 端口
     */
    private int port;
    /**
     * 最大并发数
     */
    private int maxParallel;

    public WebConfig(int port, int maxParallel) {
        this.port = port;
        this.maxParallel = maxParallel;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxParallel() {
        return maxParallel;
    }

    public void setMaxParallel(int maxParallel) {
        this.maxParallel = maxParallel;
    }
}
