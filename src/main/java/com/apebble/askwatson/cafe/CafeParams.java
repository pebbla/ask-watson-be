package com.apebble.askwatson.cafe;

import lombok.Data;

@Data
public class CafeParams {
    private String cafeName;

    private String cafePhoneNum;

    private String website;

    private String address;

    private String imageUrl;

    private Long locationId;

    private Double longitude;

    private Double latitude;

    private Boolean isEnglishPossible;
}
