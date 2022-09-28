package com.apebble.askwatson.cafe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CafeSearchOptions {
    private String searchWord;                      // 검색어

    private Long locationId;                        // 위치별 조건

    private Boolean isEnglishPossible;              // 영어 가능

}
