package com.apebble.askwatson.comm.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse <T> extends CommonResponse{
    List<T> data;
}

