package com.creact.steve.retrofitsample.network.interceptors;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.creact.steve.retrofitsample.util.Utils.checkNotNull;
import static java.lang.Long.MAX_VALUE;

/**
 * Created by Steve on 2016/5/12.
 */
public abstract class BaseInterceptor implements Interceptor {
    protected Charset mCharset = Charset.forName("UTF-8");

    public void setCharset(Charset charset) {
        this.mCharset = charset;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = manipulateRequest(chain.request());
        Response response = manipulateResponse(chain.proceed(request));
        return response;
    }

    /**
     *
     * @param request
     * @param name
     * @param value
     */
    public void addHeader(Request request, String name, String value) {
        checkNotNull(request, "request can't be null");
        request.headers()
                .newBuilder()
                .add(name, value);
    }

    /**
     *
     * @param request
     * @param headers
     */
    public void addHeader(Request request, Map<String,String> headers) {
        checkNotNull(request, "request can't be null");
        checkNotNull(headers, "headers can't be null");
        Headers newHeaders = Headers.of(headers);
        request.headers()
                .newBuilder()
                .add(newHeaders.toString());
    }

    /**
     *
     * @param request
     * @param name
     */
    public void removeHeaders(Request request, String name) {
        checkNotNull(request, "request can't be null");
        request.headers()
                .newBuilder()
                .removeAll(name);
    }

    /**
     *
     * @param response
     * @param name
     * @param value
     */
    public void addHeader(Response response, String name, String value) {
        checkNotNull(response, "response can't be null");
        response.headers()
                .newBuilder()
                .add(name, value);
    }

    /**
     *
     * @param response
     * @param headers
     */
    public void addHeader(Response response, Map<String,String> headers) {
        checkNotNull(response, "response can't be null");
        checkNotNull(headers, "headers can't be null");
        Headers newHeaders = Headers.of(headers);
        response.headers()
                .newBuilder()
                .add(newHeaders.toString());
    }

    /**
     *
     * @param response
     * @param name
     */
    public void removeHeaders(Response response, String name) {
        checkNotNull(response, "response can't be null");
        response.headers()
                .newBuilder()
                .removeAll(name);
    }

    /**
     *
     * @param request
     * @param charset
     * @return
     */
    public String getRequestBody(Request request, Charset charset) {
        Buffer buffer = new Buffer();
        try {
            request.body().writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = buffer.readString(charset);
        return body;
    }

    /**
     *
     * @param request
     * @return
     */
    public String getUrl(Request request) {
        checkNotNull(request, "request can't be null");
        return request.url().toString();
    }

    /**
     *
     * @param response
     * @return
     * @throws EOFException
     */
    public String getResponseBody(Response response) throws EOFException {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
            return "";
        }
        Buffer buffer = source.buffer();
        Charset charset = mCharset;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(mCharset);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
                System.out.println("UnsupportedCharset:" + mCharset.toString());
                return "";
            }
        }
        if (!isPlaintext(buffer)) {
            System.out.println("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
            return "";
        }
        if (responseBody.contentLength() != 0) {
            return buffer.clone().readString(charset);
        }
        return "";
    }

    /**
     *
     * @param request
     * @return
     */
    public Request manipulateRequest(Request request) {
        return request;
    }

    /**
     *
     * @param response
     * @return
     */
    public Response manipulateResponse(Response response) {
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    public boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                if (Character.isISOControl(prefix.readUtf8CodePoint())) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
