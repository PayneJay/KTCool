package com.ktcool.common.network;

/**
 * 回调接口
 */
public interface ICallback<T> {
    /**
     * 成功回调
     */
    void onSuccess(T response);

    /**
     * 失败回调
     */
    void onFailure(String msg);
}
