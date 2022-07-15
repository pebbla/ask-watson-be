package com.apebble.askwatson.comm.exception;

public class EscapeCompleteNotFoundException extends RuntimeException{
    public EscapeCompleteNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public EscapeCompleteNotFoundException(String msg) {
        super(msg);
    }

    public EscapeCompleteNotFoundException() {
        super();
    }
}
