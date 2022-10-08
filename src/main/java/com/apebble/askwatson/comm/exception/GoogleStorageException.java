package com.apebble.askwatson.comm.exception;

public class GoogleStorageException extends RuntimeException{
    public GoogleStorageException(String msg, Throwable t) {
        super(msg, t);
    }

    public GoogleStorageException(String msg) {
        super(msg);
    }

    public GoogleStorageException() {
        super();
    }
}
