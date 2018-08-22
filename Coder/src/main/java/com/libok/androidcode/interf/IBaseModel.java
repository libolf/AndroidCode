package com.libok.androidcode.interf;

/**
 * @author liboK
 * @date 2018/6/26 0026 5:15
 * @e-mail libolf@outlook.com
 * @description MVP模式 BaseModel
 */
public interface IBaseModel<T> {

    /**
     * 成功获取数据
     *
     * @param data 数据
     */
    void onSuccess(T data);

    /**
     * 获取数据失败
     *
     * @param msg 失败信息
     */
    void onFailure(String msg);

    /**
     * 获取数据出错
     *
     * @param tr 错误信息
     */
    void onError(Throwable tr);

    /**
     * 获取数据结束，不论是否成功都会回调
     */
    void onComplete();

}
