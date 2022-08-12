package com.apebble.askwatson.cafe;

import lombok.Data;

@Data
public class CafeParams {
    private String cafeName;

    private String cafePhoneNum;

    private Long locationId;

    private Long companyId;

    private Double latitude;

    private Double longitude;

    private Boolean isEnglishPossible;
}
