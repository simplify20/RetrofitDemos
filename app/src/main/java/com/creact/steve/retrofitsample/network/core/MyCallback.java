package com.creact.steve.retrofitsample.network.core;

/**
 * Created by Administrator on 2016/5/6.
 */
public interface MyCallback<T> {

    void onResponse(MyCall<T> call, MyResponse<T> response);
    void onFailure(MyCall<T> call, Throwable t);
}
