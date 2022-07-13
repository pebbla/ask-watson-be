package com.apebble.askwatson.comm.exception;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public LocationNotFoundException(String msg) {
        super(msg);
    }

    public LocationNotFoundException() {
        super();
    }
}
