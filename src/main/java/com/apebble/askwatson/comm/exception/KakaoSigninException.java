package com.apebble.askwatson.comm.exception;

public class KakaoSigninException extends RuntimeException{
    public KakaoSigninException(String msg, Throwable t) {
        super(msg, t);
    }

    public KakaoSigninException(String msg) {
        super(msg);
    }

    public KakaoSigninException() {
        super();
    }
}
