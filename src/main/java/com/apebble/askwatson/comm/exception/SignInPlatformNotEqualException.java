package com.apebble.askwatson.comm.exception;

public class SignInPlatformNotEqualException extends RuntimeException{
    public SignInPlatformNotEqualException(String msg, Throwable t) {
        super(msg, t);
    }

    public SignInPlatformNotEqualException(String msg) {
        super(msg);
    }

    public SignInPlatformNotEqualException() {
        super();
    }
}
