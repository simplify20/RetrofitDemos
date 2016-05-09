package com.creact.steve.retrofitsample.network.core;


import okhttp3.Request;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyRequest {

    private Request actual;

    public MyRequest(Request request) {
        this.actual = request;
    }

    public Request getActual() {
        return actual;
    }
}
