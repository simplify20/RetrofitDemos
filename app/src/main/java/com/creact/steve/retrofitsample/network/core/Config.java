package com.creact.steve.retrofitsample.network.core;


import com.creact.steve.retrofitsample.biz.github.ApiConstants;
import com.creact.steve.retrofitsample.network.HttpClientWrapper;
import com.creact.steve.retrofitsample.network.core.calladapter.MyCallAdapterFactory;
import com.creact.steve.retrofitsample.network.util.HttpClientFactory;

import java.util.concurrent.Executor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.creact.steve.retrofitsample.util.Utils.checkNotNull;

/**
 * Created by Administrator on 2016/5/9.
 */
public final class Config {


    private Retrofit.Builder mBuilder;

    public static Config newConfig() {
        return new Config(new Retrofit.Builder());
    }

    public static Config getDefault(String baseUrl) {
        Retrofit.Builder defaultBuilder = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(HttpClientFactory.newLoggingClient())
                .addCallAdapterFactory(MyCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return new Config(defaultBuilder);

    }

    public static Config getDefault() {
        return getDefault(ApiConstants.BASE_URL);
    }


    private Config(Retrofit.Builder builder) {
        this.mBuilder = builder;
    }


    public Retrofit.Builder builder() {
        return mBuilder;
    }

    public Config baseUrl(String baseUrl) {
        mBuilder.baseUrl(baseUrl);
        return this;
    }

    public Config client(HttpClientWrapper httpClientWrapper) {
        mBuilder.client(checkNotNull(httpClientWrapper, "httpClientWrapper can't be null").getActual());
        return this;
    }

    public Config callFactory(CallFactoryWrapper callFactoryWrapper) {
        mBuilder.callFactory(checkNotNull(callFactoryWrapper, "callFactoryWrapper can't be null").getActual());
        return this;
    }

    public Config addConverterFactory(ConverterFactoryWrapper converterFactoryWrapper) {
        mBuilder.addConverterFactory(checkNotNull(converterFactoryWrapper, "converterFactoryWrapper can't be null").getActual());
        return this;
    }

    public Config addCallAdapterFactory(CallAdapterFactoryWrapper callAdapterFactoryWrapper) {
        mBuilder.addCallAdapterFactory(checkNotNull(callAdapterFactoryWrapper, "callAdapterFactoryWrapper can't be null").getActual());
        return this;
    }

    public Config callbackExecutor(Executor executor) {
        mBuilder.callbackExecutor(executor);
        return this;
    }

    public Config validateEagerly(boolean validateEagerly) {
        mBuilder.validateEagerly(validateEagerly);
        return this;
    }


}
