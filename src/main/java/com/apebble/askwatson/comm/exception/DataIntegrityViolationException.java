package com.apebble.askwatson.comm.exception;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String msg, Throwable t) {
        super(msg, t);
    }

    public DataIntegrityViolationException(String msg) {
        super(msg);
    }

    public DataIntegrityViolationException() {
        super();
    }
}
