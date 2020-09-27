package com.ktcool.common.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMode {
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
}
