package com.creact.steve.retrofitsample.network.progress;

/**
 * Created by Administrator on 2016/5/8.
 */
public interface ProgressListener {

    void onProgress(long currentBytes, long contentLength, boolean done);
}
