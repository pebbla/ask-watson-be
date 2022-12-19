package com.apebble.askwatson.theme;

import javax.annotation.Nullable;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.theme.category.Category;
import lombok.*;

@Getter @Setter
public class ThemeDto {

    @Getter @Setter
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

        public Response(Theme entity) {
            this.id = entity.getId();
            this.themeName = entity.getThemeName();
            this.themeExplanation = entity.getThemeExplanation();
            this.category = new CategoryDto(entity.getCategory());
            this.difficulty = entity.getDifficulty();
            this.timeLimit = entity.getTimeLimit();
            this.minNumPeople = entity.getMinNumPeople();
            this.price = entity.getPrice();
            this.reservationUrl = entity.getReservationUrl();
            this.imageUrl = entity.getImageUrl();
            this.heartCount = entity.getHeartCount();
            this.escapeCount = entity.getEscapeCount();
            this.reviewCount = entity.getReviewCount();
            this.rating = entity.getRating();
            this.deviceRatio = entity.getDeviceRatio();
            this.activity = entity.getActivity();
            this.isAvailable = entity.isAvailable();
            this.cafe = new CafeDto(entity.getCafe());
        }
    }

    @Data
    private static class CafeDto {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private Long locationId=null;

        public CafeDto(Cafe entity) {
            this.id = entity.getId();
            this.cafeName = entity.getCafeName();
            this.cafePhoneNum = entity.getCafePhoneNum();
            if(!entity.isLocationNull()) this.locationId = entity.getLocation().getId();
        }
    }

    @Data
    private static class CategoryDto {
        private Long id;
        private String categoryName;

        public CategoryDto(Category entity) {
            this.id = entity.getId();
            this.categoryName = entity.getCategoryName();
        }
    }
}
