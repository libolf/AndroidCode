package com.libok.androidcode.model;

import android.os.Handler;

import com.libok.androidcode.interf.IBaseModel;

/**
 * @author liboK
 * @date 2018/6/26 0026 5:20
 * @e-mail libolf@outlook.com
 * @description
 */
public class MvpModel {

    public static void getData(final IBaseModel iBaseModel) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iBaseModel.onSuccess("success");
                iBaseModel.onComplete();
            }
        }, 2000);
    }
}
