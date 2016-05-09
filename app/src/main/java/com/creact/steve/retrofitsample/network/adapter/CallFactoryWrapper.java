package com.creact.steve.retrofitsample.network.adapter;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CallFactoryWrapper {

    private Call.Factory actual;

    public static CallFactoryWrapper newHttpClientFactory(){
        return new CallFactoryWrapper(new OkHttpClient());
    }
    private CallFactoryWrapper(Call.Factory actual) {
        this.actual = actual;
    }

    public Call.Factory getActual() {
        return actual;
    }
}
