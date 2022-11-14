package com.apebble.askwatson.comm.exception;

public class ServerException extends RuntimeException {
    
    public ServerException(String msg, Throwable t) {
        super(msg, t);
    }

    public ServerException(String msg) {
        super(msg);
    }

    public ServerException() {
        super();
    }

}
