package com.creact.steve.retrofitsample.network.interceptors;

import com.creact.steve.retrofitsample.network.HttpConfig;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Steve on 2016/5/12.
 */
public class HttpConfigInterceptor extends BaseInterceptor {

    private HttpConfig mConfig;

    public void setConfig(HttpConfig config) {
        this.mConfig = config;
    }

    @Override
    public Request manipulateRequest(Request request) {
        //read config
        return super.manipulateRequest(request);
    }

    @Override
    public Response manipulateResponse(Response response) {
        //read config
        return super.manipulateResponse(response);
    }

}
