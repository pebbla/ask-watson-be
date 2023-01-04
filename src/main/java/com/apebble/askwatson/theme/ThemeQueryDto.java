package com.apebble.askwatson.theme;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ThemeQueryDto {

    @Data
    public static class Response {
        private Long id;
        private String themeName;
        private String themeExplanation;
        private CategoryDto category;
        private double difficulty;
        private int timeLimit;
        private int minNumPeople;
        private int price;
        private String reservationUrl;
        private String imageUrl;
        private int heartCount;
        private int escapeCount;
        private int reviewCount;
        private double rating;
        private double deviceRatio;
        private double activity;
        private boolean isAvailable;
        private CafeDto cafe;
        private boolean isHearted;
        private boolean isChecked;

        @QueryProjection
        public Response(Long id,
                        String themeName,
                        String themeExplanation,
                        Long categoryId, String categoryName,
                        double difficulty,
                        int timeLimit,
                        int minNumPeople,
                        int price,
                        String reservationUrl,
                        String imageUrl,
                        int heartCount,
                        int escapeCount,
                        int reviewCount,
                        double rating,
                        double deviceRatio,
                        double activity,
                        boolean isAvailable,
                        Long cafeId, String cafeName, String cafePhoneNum, Long locationId,
                        boolean isHearted,
                        boolean isChecked) {
            this.id = id;
            this.themeName = themeName;
            this.themeExplanation = themeExplanation;
            this.category = new CategoryDto(categoryId, categoryName);
            this.difficulty = difficulty;
            this.timeLimit = timeLimit;
            this.minNumPeople = minNumPeople;
            this.price = price;
            this.reservationUrl = reservationUrl;
            this.imageUrl = imageUrl;
            this.heartCount = heartCount;
            this.escapeCount = escapeCount;
            this.reviewCount = reviewCount;
            this.rating = rating;
            this.deviceRatio = deviceRatio;
            this.activity = activity;
            this.isAvailable = isAvailable;
            this.cafe = new CafeDto(cafeId,cafeName, cafePhoneNum, locationId);
            this.isHearted = isHearted;
            this.isChecked = isChecked;
        }
    }

    @Data
    public static class WebResponse {
        private Long id;
        private String themeName;
        private String themeExplanation;
        private CategoryDto category;
        private double difficulty;
        private Integer timeLimit;
        private Integer minNumPeople;
        private Integer price;
        private String reservationUrl;
        private String imageUrl;
        private int heartCount;
        private int escapeCount;
        private int reviewCount;
        private double rating;
        private double deviceRatio;
        private double activity;
        private boolean isAvailable;
        private CafeDto cafe;

        @QueryProjection
        public WebResponse(Long id,
                           String themeName,
                           String themeExplanation,
                           Long categoryId, String categoryName,
                           double difficulty,
                           int timeLimit,
                           int minNumPeople,
                           int price,
                           String reservationUrl,
                           String imageUrl,
                           int heartCount,
                           int escapeCount,
                           int reviewCount,
                           double rating,
                           double deviceRatio,
                           double activity,
                           boolean isAvailable,
                           Long cafeId, String cafeName, String cafePhoneNum, Long locationId) {
            this.id = id;
            this.themeName = themeName;
            this.themeExplanation = themeExplanation;
            this.category = new CategoryDto(categoryId, categoryName);
            this.difficulty = difficulty;
            this.timeLimit = timeLimit;
            this.minNumPeople = minNumPeople;
            this.price = price;
            this.reservationUrl = reservationUrl;
            this.imageUrl = imageUrl;
            this.heartCount = heartCount;
            this.escapeCount = escapeCount;
            this.reviewCount = reviewCount;
            this.rating = rating;
            this.deviceRatio = deviceRatio;
            this.activity = activity;
            this.isAvailable = isAvailable;
            this.cafe = new CafeDto(cafeId, cafeName, cafePhoneNum, locationId);
        }
    }

    @Data
    private static class CafeDto {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private Long locationId;

        public CafeDto(Long id, String cafeName, String cafePhoneNum, Long locationId) {
            this.id = id;
            this.cafeName = cafeName;
            this.cafePhoneNum = cafePhoneNum;
            this.locationId = locationId;
        }
    }

    @Data
    private static class CategoryDto {
        private Long id;
        private String categoryName;

        public CategoryDto(Long id, String categoryName) {
            this.id = id;
            this.categoryName = categoryName;
        }
    }
}
