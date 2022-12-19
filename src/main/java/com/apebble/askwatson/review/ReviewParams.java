package com.apebble.askwatson.review;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewParams {

    private double difficulty;              // 난이도

    private double timeTaken;               // 걸린시간

    private Integer usedHintNum;            // 사용한 힌트 갯수

    private double rating;                  // 별점

    private double deviceRatio;             // 장치 비율(1, 3, 5)

    private double activity;                // 활동성(1, 3, 5)

    private String content;                 // 내용

    private String checkDate;            // 탈출 날짜
}
