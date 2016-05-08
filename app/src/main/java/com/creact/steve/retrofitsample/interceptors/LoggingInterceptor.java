package com.creact.steve.retrofitsample.interceptors;

import com.creact.steve.retrofitsample.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/8.
 */
public class LoggingInterceptor implements Interceptor {
    private String TAG  = "LoggingInterceptor:";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //log request
        Logger.debug(TAG,"method:"+request.method());
        Logger.debug(TAG,"request url:"+request.url().toString());
        Logger.debug(TAG,"headers:"+request.headers().toString());
        //log response
        Response response = chain.proceed(request);
        Logger.debug(TAG,"response headers:"+response.headers().toString());
        Logger.debug(TAG,"response body:"+response.body().string());
        return response;
    }
}
