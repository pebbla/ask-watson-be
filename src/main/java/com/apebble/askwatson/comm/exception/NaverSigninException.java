package com.apebble.askwatson.comm.exception;

public class NaverSigninException extends RuntimeException{
    public NaverSigninException(String msg, Throwable t) {
        super(msg, t);
    }

    public NaverSigninException(String msg) {
        super(msg);
    }

    public NaverSigninException() {
        super();
    }
}
