package com.apebble.askwatson.faq;

import lombok.Data;

@Data
public class FaqParams {

    private Long id;                // pk

    private String title;           // 제목

    private String content;         // 내용

}
