package com.apebble.askwatson.heart;

import com.apebble.askwatson.theme.ThemeQueryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class HeartQueryDto {

    @Data
    public static class Response {
        private Long id;
        private ThemeQueryDto.Response theme;

        @QueryProjection
        public Response(
                Long id,
                Long themeId,
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
                boolean isChecked
        ) {
            this.id = id;
            this.theme = new ThemeQueryDto.Response(
                    themeId,
                    themeName,
                    themeExplanation,
                    categoryId, categoryName,
                    difficulty,
                    timeLimit,
                    minNumPeople,
                    price,
                    reservationUrl,
                    imageUrl,
                    heartCount,
                    escapeCount,
                    reviewCount,
                    rating,
                    deviceRatio,
                    activity,
                    isAvailable,
                    cafeId, cafeName, cafePhoneNum, locationId,
                    isHearted,
                    isChecked
            );
        }
    }
}
