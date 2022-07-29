package com.apebble.askwatson.review;

import lombok.Data;

@Data
public class ReviewParams {

    private double rating;          // 별점

    private double difficulty;      // 난이도

    private double timeTaken;       // 걸린시간

    private Integer usedHintNum;     // 사용한 힌트 갯수

    private String content;         // 내용
    
}