package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.location.Location;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
public class CafeDto {

    @Data
    public static class Request {
        private String cafeName;
        private String cafePhoneNum;
        private String website;
        private String address;
        private String imageUrl;
        private Long locationId;
        private Double longitude;
        private Double latitude;
        private Boolean isEnglishPossible;
        private Boolean isAvailable;
    }


    @Data
    public static class Response {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private Location location;
        private String website;
        private String address;
        private String imageUrl;
        private PointDto geography = null;
        private int reviewCount;
        private double rating;
        private Boolean isEnglishPossible;
        private boolean isAvailable;

        public Response(Cafe entity) {
            this.id = entity.getId();
            this.cafeName = entity.getCafeName();
            this.cafePhoneNum = entity.getCafePhoneNum();
            this.location = entity.getLocation();
            this.website = entity.getWebsite();
            this.address = entity.getAddress();
            this.imageUrl = entity.getImageUrl();
            if(!entity.isGeographyNull()) this.geography = new PointDto(entity.getGeography());
            this.reviewCount = entity.getReviewCount();
            this.rating = entity.getRating();
            this.isEnglishPossible = entity.getIsEnglishPossible();
            this.isAvailable = entity.isAvailable();
        }
    }

    @Data
    private static class PointDto {
        private Double longitude;
        private Double latitude;

        public PointDto(Point entity) {
            this.longitude = entity.getX();
            this.latitude = entity.getY();
        }
    }
}
