package com.apebble.askwatson.comm.exception;

public class NoticeNotFoundException extends RuntimeException{

    public NoticeNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public NoticeNotFoundException(String msg) {
        super(msg);
    }

    public NoticeNotFoundException() {
        super();
    }

}
