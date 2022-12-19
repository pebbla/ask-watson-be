package com.apebble.askwatson.comm.exception;

public class CheckUndeletableException extends RuntimeException{
    public CheckUndeletableException(String msg, Throwable t) {
        super(msg, t);
    }

    public CheckUndeletableException(String msg) {
        super(msg);
    }

    public CheckUndeletableException() {
        super();
    }
}
