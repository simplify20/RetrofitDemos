package com.creact.steve.retrofitsample.network.adapter.http;

import okhttp3.Response;

/**
 * Created by Steve on 2016/5/12.
 */
public class MyResponse {

    Response actual;

    public MyResponse(Response actual) {
        this.actual = actual;
    }

    public Response getActual() {
        return actual;
    }
}
