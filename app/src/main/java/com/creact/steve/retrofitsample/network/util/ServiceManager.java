package com.creact.steve.retrofitsample.network.util;

import com.creact.steve.retrofitsample.network.adapter.Builder;

import retrofit2.Retrofit;

import static com.creact.steve.retrofitsample.util.Utils.checkNotNull;

/**
 * Created by Administrator on 2016/5/9.
 */
public final class ServiceManager {
    private static ServiceManager sInstance;

    public static ServiceManager getInstance() {
        if (sInstance == null) {
            sInstance = new ServiceManager();
        }
        return sInstance;
    }

    private ServiceManager() {
    }

    public  <T> T getService(Builder builder, Class<T> serviceClass) {
        checkNotNull(builder, "builder can't be null");
        Retrofit retrofit = builder
                .actual()
                .build();
        T result = retrofit.create(serviceClass);
        return result;
    }
}
