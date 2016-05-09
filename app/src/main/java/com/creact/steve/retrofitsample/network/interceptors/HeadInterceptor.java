package com.creact.steve.retrofitsample.network.interceptors;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/8.
 */
public class HeadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //add request headers
        Headers newHeaders = request
                .headers()
                .newBuilder()
                .add("Custom-Header","custom header")
                .build();
        Request newRequest = request.
                newBuilder()
                .headers(newHeaders)
                .build();
        return chain.proceed(newRequest);
    }
}
