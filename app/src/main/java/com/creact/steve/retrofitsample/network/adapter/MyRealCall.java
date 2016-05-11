package com.creact.steve.retrofitsample.network.adapter;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2016/5/6.
 */
public class MyRealCall<T> implements MyCall<T> {
    Call<T> mDelegate;

    MainThreadExecutor callbackExecutor = new MainThreadExecutor();

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
            public void onResponse(Call<T> call, final Response<T> response) {
                callbackExecutor.execute(new Runnable() {
                    @Override public void run() {
                        if (mDelegate.isCanceled()) {
                            // Emulate OkHttp's behavior of throwing/delivering an IOException on cancellation.
                            callback.onFailure(MyRealCall.this, new IOException("Canceled"));
                        } else {
                            callback.onResponse(MyRealCall.this, new MyResponse(response));
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<T> call, final Throwable t) {
                callbackExecutor.execute(new Runnable() {
                    @Override public void run() {
                        callback.onFailure(MyRealCall.this, t);
                    }
                });
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

    static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override public void execute(Runnable r) {
            handler.post(r);
        }
    }
}
