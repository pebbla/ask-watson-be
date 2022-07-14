package com.apebble.askwatson.comm.exception;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public ReportNotFoundException(String msg) {
        super(msg);
    }

    public ReportNotFoundException() {
        super();
    }
}
