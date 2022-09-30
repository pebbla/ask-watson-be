package com.apebble.askwatson.comm.exception;

public class EscapeCompleteUndeletableException extends RuntimeException{
    public EscapeCompleteUndeletableException(String msg, Throwable t) {
        super(msg, t);
    }

    public EscapeCompleteUndeletableException(String msg) {
        super(msg);
    }

    public EscapeCompleteUndeletableException() {
        super();
    }
}
