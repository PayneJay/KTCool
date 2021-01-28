package com.ktcool.common.network;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMode implements IHttp{
    private static volatile RetrofitMode instance;

    public static RetrofitMode getInstance() {
        if (null == instance) {
            synchronized (RetrofitMode.class) {
                if (null == instance) {
                    instance = new RetrofitMode();
                }
            }
        }
        return instance;
    }

    private RetrofitMode() {
        new Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public <T extends BaseBean> void get(String url, Map<String, String> params, ICallback<T> iCallback, Class<T> clazz) {

    }

    @Override
    public <T extends BaseBean> void post(String url, String params, ICallback<T> iCallback, Class<T> clazz) {

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
}
