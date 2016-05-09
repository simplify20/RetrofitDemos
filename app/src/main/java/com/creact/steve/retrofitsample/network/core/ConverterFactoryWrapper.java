package com.creact.steve.retrofitsample.network.core;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ConverterFactoryWrapper {

    private Converter.Factory actual;

    public static ConverterFactoryWrapper newGsonConverterFactory(){
        return new ConverterFactoryWrapper(GsonConverterFactory.create());
    }

    private ConverterFactoryWrapper(Converter.Factory actual) {
        this.actual = actual;
    }

    public Converter.Factory getActual() {
        return actual;
    }
}
