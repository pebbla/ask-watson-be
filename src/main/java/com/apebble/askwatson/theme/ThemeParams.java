package com.apebble.askwatson.theme;

import lombok.Data;

@Data
public class ThemeParams {
    private String themeName;

    private String themeExplanation;

    private String category;

    private double difficulty;

    private int timeLimit;
}
