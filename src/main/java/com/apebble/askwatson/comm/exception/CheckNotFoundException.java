package com.apebble.askwatson.comm.exception;

public class CheckNotFoundException extends RuntimeException{
    public CheckNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CheckNotFoundException(String msg) {
        super(msg);
    }

    public CheckNotFoundException() {
        super();
    }
}
