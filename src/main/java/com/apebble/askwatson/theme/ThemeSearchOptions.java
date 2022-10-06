package com.apebble.askwatson.theme;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThemeSearchOptions {
    private String searchWord;                  // 검색어

    private Long locationId;                    // 위치별 조건

    private Long categoryId;                    // 카테고리별 조건

    private Integer minNumPeople;               // 최소인원수 조건

    private Double difficultyRangeFrom;         // 난이도범위 시작
    private Double difficultyRangeTo;           // 난이도범위 끝

    private Double deviceRatioRangeFrom;        // 장치비율 범위 시작
    private Double deviceRatioRangeTo;          // 장치비율 범위 끝

    private Double activityRangeFrom;           // 활동성 범위 시작
    private Double activityRangeTo;             // 활동성 범위 끝

    private Integer timeLimitRangeFrom;         // 시간제한 필터
    private Integer timeLimitRangeTo;           // 시간제한 필터
}
