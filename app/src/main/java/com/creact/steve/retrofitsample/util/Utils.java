package com.creact.steve.retrofitsample.util;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Utils {

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
