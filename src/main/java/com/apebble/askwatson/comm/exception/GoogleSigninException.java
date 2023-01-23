package com.apebble.askwatson.comm.exception;

public class GoogleSigninException extends RuntimeException{
    public GoogleSigninException(String msg, Throwable t) {
        super(msg, t);
    }

    public GoogleSigninException(String msg) {
        super(msg);
    }

    public GoogleSigninException() {
        super();
    }
}
