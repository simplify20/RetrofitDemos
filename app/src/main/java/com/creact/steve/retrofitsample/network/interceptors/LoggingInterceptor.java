package com.creact.steve.retrofitsample.network.interceptors;

import com.creact.steve.retrofitsample.util.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Administrator on 2016/5/8.
 */
public class LoggingInterceptor extends BaseInterceptor{
    private String TAG = "LoggingInterceptor:";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //log request
        Logger.debug(TAG, "request method:" + request.method());
        Logger.debug(TAG, "request url:" + request.url().toString());
        Logger.debug(TAG, "request headers:" + request.headers().toString());
        //log response
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        Logger.debug(TAG, "<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + ')');
        Logger.debug(TAG, "response headers:" + response.headers().toString());
        //response body can only read once,after reading it will be closed
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = mCharset;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(mCharset);
            } catch (UnsupportedCharsetException e) {
                Logger.debug(TAG, "Couldn't decode the response body; charset is likely malformed.");
                return response;
            }
        }
        if (!isPlaintext(buffer)) {
            Logger.debug(TAG, "<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
            return response;
        }
        if (responseBody.contentLength() != 0) {
            Logger.debug(TAG, "response body:" + buffer.clone().readString(charset));
        }
        Logger.debug(TAG, "<-- END HTTP (" + buffer.size() + "-byte body)");
        return response;
    }

}
