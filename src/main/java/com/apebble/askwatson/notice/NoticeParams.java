package com.apebble.askwatson.notice;

import lombok.Data;

@Data
public class NoticeParams {

    private Long id;                    // pk

    private String title;               // 제목

    private String content;             //내용
}
