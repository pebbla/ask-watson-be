package com.apebble.askwatson.cafe.location;

import lombok.Data;

@Data
public class LocationParams {

    private String state;                   // 대분류(행정구역)

    private String city;                    // 소분류(도시명)

}
