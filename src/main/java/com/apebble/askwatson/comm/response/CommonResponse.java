package com.apebble.askwatson.comm.response;

import lombok.Getter;

@Getter
public class CommonResponse {
    boolean success;
    int code;
    String message;
}

