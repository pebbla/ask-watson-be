package com.apebble.askwatson.comm.exception;

public class FaqNotFoundException extends RuntimeException{
    public FaqNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public FaqNotFoundException(String msg) {
        super(msg);
    }

    public FaqNotFoundException() {
        super();
    }
}
