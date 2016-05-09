package com.creact.steve.retrofitsample.network.adapter;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/6.
 */
public interface MyCall<T> {

    MyResponse<T> execute() throws IOException;
    void enqueue(MyCallback<T> callback);
    boolean isExecuted();
    void cancel();
    boolean isCanceled();
    MyCall<T> clone();
    MyRequest request();
}
