package com.creact.steve.retrofitsample.network.core;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2016/5/6.
 */
public class MyRealCall<T> implements MyCall<T> {
    Call<T> mDelegate;

    public MyRealCall(Call<T> call) {
        if (call == null) {
            throw new IllegalArgumentException("delegate call can not be null");
        }
        this.mDelegate = call;
    }

    @Override
    public MyResponse<T> execute() throws IOException {
        return new MyResponse<>(mDelegate.execute());
    }

    @Override
    public void enqueue(final MyCallback<T> callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback can not be null");
        }
        mDelegate.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callback.onResponse(MyRealCall.this,new MyResponse<T>(response));
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onFailure(MyRealCall.this,t);
            }
        });
    }


    @Override
    public boolean isExecuted() {
        return mDelegate.isExecuted();
    }

    @Override
    public void cancel() {
        mDelegate.cancel();
    }

    @Override
    public boolean isCanceled() {
        return mDelegate.isCanceled();
    }

    @Override
    public MyCall<T> clone() {
        return new MyRealCall<>(mDelegate.clone());
    }

    @Override
    public MyRequest request() {
        return new MyRequest(mDelegate.request());
    }
}
