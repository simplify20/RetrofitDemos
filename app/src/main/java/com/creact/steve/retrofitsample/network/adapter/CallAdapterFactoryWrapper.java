package com.creact.steve.retrofitsample.network.adapter;

import retrofit2.CallAdapter;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CallAdapterFactoryWrapper {

    private CallAdapter.Factory actual;

    public static CallAdapterFactoryWrapper newMyCallAdapterFactory(){
        return new CallAdapterFactoryWrapper(MyCallAdapterFactory.create());
    }
    public static CallAdapterFactoryWrapper newRxCallAdapterFactory(){
        return new CallAdapterFactoryWrapper(RxJavaCallAdapterFactory.create());
    }
    private CallAdapterFactoryWrapper(CallAdapter.Factory actual) {
        this.actual = actual;
    }

    public CallAdapter.Factory getActual() {
        return actual;
    }
}
