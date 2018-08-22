package com.libok.androidcode.interf;

/**
 * @author liboK
 * @date 2018/6/26 0026 5:03
 * @e-mail libolf@outlook.com
 * @description MVP模式 BaseView
 */
public interface IBaseView {

    /**
     * 显示加载进度框
     */
    void showLoading();


    /**
     * 隐藏进度框
     */
    void dismissLoading();

    /**
     * 显示数据
     *
     * @param data 数据
     */
    void showData(String data);

    /**
     * 显示失败信息
     *
     * @param msg 失败信息
     */
    void showFailure(String msg);

    /**
     * 发生错误
     *
     * @param throwable 错误信息
     */
    void showError(Throwable throwable);
}
