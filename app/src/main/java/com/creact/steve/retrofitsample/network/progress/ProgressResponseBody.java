package com.creact.steve.retrofitsample.network.progress;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody mDelegate;
    private ProgressListener mProgressListener;
    private BufferedSource mBufferedSource;

    public static ProgressResponseBody create(ResponseBody delegate,ProgressListener progressListener){
        return new ProgressResponseBody(delegate,progressListener);
    }

    private ProgressResponseBody(ResponseBody delegate, ProgressListener progressListener) {
        if (delegate == null) {
            throw new IllegalArgumentException("delegate response body can not be null");
        }
        if (progressListener == null) {
            throw new IllegalArgumentException("progress listener can not be null");
        }
        this.mDelegate = delegate;
        this.mProgressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength() {
        return mDelegate.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(wrapperSource(mDelegate.source()));
        }
        return mBufferedSource;
    }

    private Source wrapperSource(BufferedSource source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                //if reading is finished ,bytesRead will be -1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //if has no content length,contentLength() will return -1
                if (mProgressListener != null) {
                    mProgressListener.onProgress(totalBytesRead, mDelegate.contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }
}
