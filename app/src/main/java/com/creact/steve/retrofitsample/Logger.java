package com.creact.steve.retrofitsample;

import android.util.Log;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Logger {
    private static boolean isDebug = BuildConfig.DEBUG;

    public static void debug(String tag, String message) {
        if (isDebug) {
            Log.d(tag, message);
        }
    }
}
