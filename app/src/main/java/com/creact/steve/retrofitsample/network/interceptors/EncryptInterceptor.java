package com.creact.steve.retrofitsample.network.interceptors;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpEngine;
import okio.Buffer;

/**
 * Created by Administrator on 2016/5/8.
 */
public class EncryptInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Buffer buffer = new Buffer();
        request.body().writeTo(buffer);
        String body = buffer.readString(Charset.forName("UTF-8"));
        //// TODO: 2016/5/8 encrypt request parameters
        return chain.proceed(request);
    }
}
