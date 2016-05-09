package com.creact.steve.retrofitsample.network.adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/5/9.
 */
public class MyCallAdapterFactory extends CallAdapter.Factory {
    public static MyCallAdapterFactory create() {

        return new MyCallAdapterFactory();
    }

    private MyCallAdapterFactory() {
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != MyCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    "MyCall must have generic type (e.g., MyCall<ResponseBody>)");
        }
        final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new CallAdapter<MyCall<?>>() {
            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public <R> MyCall<R> adapt(Call<R> call) {
                return new MyRealCall<>(call);
            }
        };
    }


}
