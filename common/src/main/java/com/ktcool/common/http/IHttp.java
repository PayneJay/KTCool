package com.ktcool.common.http;

import java.util.Map;

/**
 * 网络请求方式定义类
 */
public interface IHttp {
    <T extends BaseBean> void get(String url, Map<String, String> params, ICallback<T> iCallback, Class<T> clazz);

    <T extends BaseBean> void post(String url, String params, ICallback<T> iCallback, Class<T> clazz);

    <T extends BaseBean> void put(String url, String params, ICallback<T> iCallback, Class<T> clazz);

    <T extends BaseBean> void delete(String url, String params, ICallback<T> iCallback, Class<T> clazz);

    <T extends BaseBean> void patch(String url, String params, ICallback<T> iCallback, Class<T> clazz);
}
