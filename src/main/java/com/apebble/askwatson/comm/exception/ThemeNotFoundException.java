package com.apebble.askwatson.comm.exception;

public class ThemeNotFoundException extends RuntimeException{
    public ThemeNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ThemeNotFoundException(String msg) {
        super(msg);
    }

    public ThemeNotFoundException() {
        super();
    }
}
