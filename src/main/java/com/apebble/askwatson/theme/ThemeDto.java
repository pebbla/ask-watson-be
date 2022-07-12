package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import lombok.*;

@Getter @Setter
public class ThemeDto {

    @Getter @Setter
    public static class Response {
        private Long id;
        private String themeName;
        private String themeExplanation;
        private String category;
        private double difficulty;
        private int timeLimit;
        private int likeCount;
        private int escapeCount;
        private CafeDto cafe;

        public Response(Theme entity) {
            this.id = entity.getId();
            this.themeName = entity.getThemeName();
            this.themeExplanation = entity.getThemeExplanation();
            this.category = entity.getCategory();
            this.difficulty = entity.getDifficulty();
            this.timeLimit = entity.getTimeLimit();
            this.likeCount = entity.getLikeCount();
            this.escapeCount = entity.getEscapeCount();
            this.cafe = new CafeDto(entity.getCafe());
        }
    }

    @Getter @NoArgsConstructor
    public static class CafeDto {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private String locationSort;
        private String company;

        public CafeDto(Cafe entity) {
            this.id = entity.getId();
            this.cafeName = entity.getCafeName();
            this.cafePhoneNum = entity.getCafePhoneNum();
            this.locationSort = entity.getLocationSort();
            this.company = entity.getCompany();
        }
    }
}
