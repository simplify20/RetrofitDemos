package com.creact.steve.retrofitsample.network.core;


import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MyResponse<T> {

    private Response<T> actual;

    public MyResponse(Response<T> response) {
        this.actual = response;
    }

    public Response<T> getActual() {
        return actual;
    }

    public int code(){
        return actual.code();
    }

    public T body(){
        return actual.body();
    }

    public String errorBodyStr(){
        try {
            return actual.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
