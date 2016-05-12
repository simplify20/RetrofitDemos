package com.creact.steve.retrofitsample.network;

/**
 * Created by Steve on 2016/5/12.
 */
public class HttpConfig {
    //设置是否需要basic认证，默认需要
    private boolean isWithAuth = true;
    //设置是否需要设置cookie，默认需要．
    private boolean isSetCookie = true;
    //设置是否需要超时，默认需要．
    private boolean isNeedTimeOut = true;
    //设置是否启用响应压缩检查,默认不压缩。
    private boolean isEnableResponseDeflate = false;
    //设置是否启用请求压缩，默认不压缩
    private boolean isEnableRequestDeflate = false;

    private boolean isNeedEncrypt = true;

    public boolean isWithAuth() {
        return isWithAuth;
    }

    public boolean isSetCookie() {
        return isSetCookie;
    }

    public boolean isNeedTimeOut() {
        return isNeedTimeOut;
    }

    public boolean isEnableResponseDeflate() {
        return isEnableResponseDeflate;
    }

    public boolean isEnableRequestDeflate() {
        return isEnableRequestDeflate;
    }

    public boolean isNeedEncrypt() {
        return isNeedEncrypt;
    }

    public HttpConfig(Builder builder) {
        this.isWithAuth = builder.isWithAuth;
        this.isSetCookie = builder.isSetCookie;
        this.isNeedTimeOut = builder.isNeedTimeOut;
        this.isEnableResponseDeflate = builder.isEnableResponseDeflate;
        this.isEnableRequestDeflate = builder.isEnableRequestDeflate;
        this.isNeedEncrypt = builder.isNeedEncrypt;
    }


    public static class Builder {
        private boolean isWithAuth;
        private boolean isSetCookie;
        private boolean isNeedTimeOut;
        private boolean isEnableResponseDeflate;
        private boolean isEnableRequestDeflate;
        private boolean isNeedEncrypt;

        public Builder withAuth(boolean isWithAuth) {
            this.isWithAuth = isWithAuth;
            return this;
        }

        public Builder setCookie(boolean isSetCookie) {
            this.isSetCookie = isSetCookie;
            return this;
        }

        public Builder needTimeOut(boolean isNeedTimeOut) {
            this.isNeedTimeOut = isNeedTimeOut;
            return this;
        }

        public Builder enableResponseDeflate(boolean isEnableResponseDeflate) {
            this.isEnableResponseDeflate = isEnableResponseDeflate;
            return this;
        }

        public Builder enableRequestDeflate(boolean isEnableRequestDeflate) {
            this.isEnableRequestDeflate = isEnableRequestDeflate;
            return this;
        }

        public Builder needEncrypt(boolean isNeedEncrypt) {
            this.isNeedEncrypt = isNeedEncrypt;
            return this;
        }

        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }
}
