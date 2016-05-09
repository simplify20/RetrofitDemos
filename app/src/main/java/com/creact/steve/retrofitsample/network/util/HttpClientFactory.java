package com.creact.steve.retrofitsample.network.util;


import com.creact.steve.retrofitsample.network.interceptors.LoggingInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/5/9.
 */
public final class HttpClientFactory {

    private HttpClientFactory(){}

    public static OkHttpClient newLoggingClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        return okHttpClient;
    }
}
