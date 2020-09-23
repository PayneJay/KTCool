package com.ktcool.common.http;

/**
 * 回调接口
 */
public interface ICallback<T extends BaseBean> {
    /**
     * 成功回调
     */
    void onSuccess(T response);

    /**
     * 失败回调
     */
    void onFailure(String msg);
}
