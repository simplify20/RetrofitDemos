package com.creact.steve.retrofitsample.network.adapter;


import com.creact.steve.retrofitsample.biz.github.ApiConstants;
import com.creact.steve.retrofitsample.network.util.HttpClientFactory;

import java.util.concurrent.Executor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.creact.steve.retrofitsample.util.Utils.checkNotNull;

/**
 * Created by Administrator on 2016/5/9.
 */
public final class Builder {


    private Retrofit.Builder mBuilder;

    public static Builder newBuilder() {
        return new Builder(new Retrofit.Builder());
    }

    public static Builder getDefault(String baseUrl) {
        Retrofit.Builder defaultBuilder = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(HttpClientFactory.newLoggingClient())
                .addCallAdapterFactory(MyCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return new Builder(defaultBuilder);

    }

    public static Builder getDefault() {
        return getDefault(ApiConstants.BASE_URL);
    }


    private Builder(Retrofit.Builder builder) {
        this.mBuilder = builder;
    }


    public Retrofit.Builder actual() {
        return mBuilder;
    }

    public Builder baseUrl(String baseUrl) {
        mBuilder.baseUrl(baseUrl);
        return this;
    }

    public Builder client(HttpClientWrapper httpClientWrapper) {
        mBuilder.client(checkNotNull(httpClientWrapper, "httpClientWrapper can't be null").getActual());
        return this;
    }

    public Builder callFactory(CallFactoryWrapper callFactoryWrapper) {
        mBuilder.callFactory(checkNotNull(callFactoryWrapper, "callFactoryWrapper can't be null").getActual());
        return this;
    }

    public Builder addConverterFactory(ConverterFactoryWrapper converterFactoryWrapper) {
        mBuilder.addConverterFactory(checkNotNull(converterFactoryWrapper, "converterFactoryWrapper can't be null").getActual());
        return this;
    }

    public Builder addCallAdapterFactory(CallAdapterFactoryWrapper callAdapterFactoryWrapper) {
        mBuilder.addCallAdapterFactory(checkNotNull(callAdapterFactoryWrapper, "callAdapterFactoryWrapper can't be null").getActual());
        return this;
    }

    public Builder callbackExecutor(Executor executor) {
        mBuilder.callbackExecutor(executor);
        return this;
    }

    public Builder validateEagerly(boolean validateEagerly) {
        mBuilder.validateEagerly(validateEagerly);
        return this;
    }


}
