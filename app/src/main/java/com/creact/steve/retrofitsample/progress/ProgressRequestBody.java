package com.creact.steve.retrofitsample.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ProgressRequestBody extends RequestBody {
    private RequestBody mDelegate;
    private ProgressListener mProgressListener;
    private BufferedSink mBufferedSink;

    public static ProgressRequestBody create(RequestBody body, ProgressListener progressListener) {
        return new ProgressRequestBody(body, progressListener);
    }

    private ProgressRequestBody(RequestBody delegate, ProgressListener progressListener) {
        if (delegate == null) {
            throw new IllegalArgumentException("delegate request body can not be null");
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
    public long contentLength() throws IOException {
        return mDelegate.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(wrapperSink(sink));
        }
        mDelegate.writeTo(mBufferedSink);
        mBufferedSink.flush();
    }

    private Sink wrapperSink(BufferedSink sink) {
        //// TODO: 2016/5/8 chunked
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                bytesWritten += byteCount;
                if (contentLength == 0) {
                    contentLength = contentLength();
                }
                mProgressListener.onProgress(bytesWritten,contentLength,bytesWritten==contentLength);
            }
        };
    }
}
