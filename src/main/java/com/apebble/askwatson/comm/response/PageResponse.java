package com.apebble.askwatson.comm.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> extends CommonResponse {
    List<T> data;
    Pageable pageable;
    int totalPages;
    long totalElements;
    boolean empty;
    int numberOfElements;
    int size;
}
