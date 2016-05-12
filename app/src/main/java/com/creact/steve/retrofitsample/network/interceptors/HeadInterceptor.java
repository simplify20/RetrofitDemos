package com.creact.steve.retrofitsample.network.interceptors;

import okhttp3.Request;

/**
 * Created by Administrator on 2016/5/8.
 */
public class HeadInterceptor extends BaseInterceptor{

    @Override
    public Request manipulateRequest(Request request) {
        //add request headers
        addHeader(request,"Custom-Header","custom header");
        return request;
    }
}

