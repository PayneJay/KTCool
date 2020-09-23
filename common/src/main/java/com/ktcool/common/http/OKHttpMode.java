package com.ktcool.common.http;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okHttp网络请求
 */
public class OKHttpMode implements IHttp {
    private static volatile OKHttpMode instance;
    private OkHttpClient mClient;
    private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    public static OKHttpMode getInstance() {
        if (null == instance) {
            synchronized (OKHttpMode.class) {
                if (null == instance) {
                    instance = new OKHttpMode();
                }
            }
        }
        return instance;
    }

    private OKHttpMode() {
        mClient = new OkHttpClient();
    }


    @Override
    public <T extends BaseBean> void get(String url, Map<String, String> params, ICallback<T> iCallback, Class<T> clazz) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        parseResponse(iCallback, clazz, request);
    }

    @Override
    public <T extends BaseBean> void post(String url, String params, ICallback<T> iCallback, Class<T> clazz) {
        RequestBody body = RequestBody.create(params, MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        parseResponse(iCallback, clazz, request);
    }

    @Override
    public <T extends BaseBean> void put(String url, String params, ICallback<T> iCallback, Class<T> clazz) {

    }

    @Override
    public <T extends BaseBean> void delete(String url, String params, ICallback<T> iCallback, Class<T> clazz) {

    }

    @Override
    public <T extends BaseBean> void patch(String url, String params, ICallback<T> iCallback, Class<T> clazz) {

    }

    /**
     * 解析服务器返回数据
     *
     * @param iCallback 接口回调
     * @param clazz     对应数据实体Class
     * @param request   请求对象
     * @param <T>       对应数据实体
     */
    private <T extends BaseBean> void parseResponse(ICallback<T> iCallback, Class<T> clazz, Request request) {
        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                T t = JSON.parseObject(response.toString(), clazz);
                iCallback.onSuccess(t);
            } else {
                iCallback.onFailure(response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
            iCallback.onFailure(e.getMessage());
        }
    }
}
