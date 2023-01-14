package com.apebble.askwatson.comm.exception;

public class SuggestionNotFoundException extends RuntimeException{
    public SuggestionNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public SuggestionNotFoundException(String msg) {
        super(msg);
    }

    public SuggestionNotFoundException() {
        super();
    }
}
