package com.apebble.askwatson.comm.exception;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public CompanyNotFoundException(String msg) {
        super(msg);
    }

    public CompanyNotFoundException() {
        super();
    }
}
