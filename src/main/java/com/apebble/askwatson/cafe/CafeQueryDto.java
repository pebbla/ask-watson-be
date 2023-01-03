package com.apebble.askwatson.cafe;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
public class CafeQueryDto {

    @Data
    public static class Response {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private LocationDto location;
        private String website;
        private String address;
        private String imageUrl;
        private PointDto geography;
        private int reviewCount;
        private double rating;
        private Boolean isEnglishPossible;
        private boolean isAvailable;

        @QueryProjection
        public Response(Long id,
                        String cafeName,
                        String cafePhoneNum,
                        Long locationId, String state, String city,
                        String website,
                        String address,
                        String imageUrl,
                        Point geography,
                        int reviewCount,
                        double rating,
                        boolean isEnglishPossible,
                        boolean isAvailable) {
            this.id = id;
            this.cafeName = cafeName;
            this.cafePhoneNum = cafePhoneNum;
            this.location = new LocationDto(locationId, state, city);
            this.website = website;
            this.address = address;
            this.imageUrl = imageUrl;
            this.geography = new PointDto(geography.getX(), geography.getY());
            this.reviewCount = reviewCount;
            this.rating = rating;
            this.isEnglishPossible = isEnglishPossible;
            this.isAvailable = isAvailable;
        }
    }

    @Data
    private static class LocationDto {
        private Long id;
        private String state;
        private String city;

        public LocationDto(Long id, String state, String city) {
            this.id = id;
            this.state = state;
            this.city = city;
        }
    }

    @Data
    private static class PointDto {
        private Double longitude;
        private Double latitude;

        public PointDto(Double longitude, Double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }

}
