package com.creact.steve.retrofitsample.network;

import com.creact.steve.retrofitsample.network.util.HttpClientFactory;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/5/9.
 */
public class HttpClientWrapper {

    private OkHttpClient actual;

    public static HttpClientWrapper newHttpClientWrapper(){
        return new HttpClientWrapper(HttpClientFactory.newLoggingClient());
    }

    private HttpClientWrapper(OkHttpClient actual) {
        this.actual = actual;
    }

    public OkHttpClient getActual() {
        return actual;
    }
}
