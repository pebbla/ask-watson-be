package com.apebble.askwatson.theme;

import lombok.Data;

@Data
public class ThemeParams {
    private String themeName;

    private String themeExplanation;

    private Long categoryId;

    private int timeLimit;

    private int minNumPeople;

    private int price;

    private String reservationUrl;

    private String imageUrl;

    private Boolean isAvailable;
}
