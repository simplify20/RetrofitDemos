package com.creact.steve.retrofitsample.network.util;


import com.creact.steve.retrofitsample.network.interceptors.LoggingInterceptor;
import com.creact.steve.retrofitsample.network.progress.ProgressListener;
import com.creact.steve.retrofitsample.network.progress.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

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

    public static OkHttpClient newProgressClient(final ProgressListener progressListener){

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //intercept
                Response originalResponse = chain.proceed(chain.request());
                //wrap response body
                return originalResponse.newBuilder()
                        .body(ProgressResponseBody.create(originalResponse.body(), progressListener))
                        .build();
            }
        };

        return new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();
    }
}
