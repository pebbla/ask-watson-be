package com.apebble.askwatson.comm.exception;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReviewNotFoundException(String msg) {
        super(msg);
    }

    public ReviewNotFoundException() {
        super();
    }
}
