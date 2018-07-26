package com.libok.androidcode.presenter;

import com.libok.androidcode.interf.IBaseModel;
import com.libok.androidcode.interf.IBaseView;
import com.libok.androidcode.model.MvpModel;

/**
 * @author liboK
 * @date 2018/6/26 0026 5:24
 * @e-mail libolf@outlook.com
 * @description
 */
public class MvpPresenter {

    private IBaseView mBaseView;

    /**
     * 绑定View
     * @param baseView
     */
    public void attachView(IBaseView baseView) {
        mBaseView = baseView;
    }

    public void getData() {
        mBaseView.showLoading();
        MvpModel.getData(new IBaseModel<String>() {
            @Override
            public void onSuccess(String data) {
                mBaseView.showData(data);
            }

            @Override
            public void onFailure(String msg) {
                mBaseView.showFailure(msg);
            }

            @Override
            public void onError(Throwable tr) {
                mBaseView.showError(tr);
            }

            @Override
            public void onComplete() {
                mBaseView.dismissLoading();
            }
        });
    }

    /**
     * 解绑View
     */
    public void detachView() {
        mBaseView = null;
    }

    /**
     * @return 返回当前View是否已经绑定
     */
    public boolean isAttach() {
        return mBaseView != null;
    }
}
