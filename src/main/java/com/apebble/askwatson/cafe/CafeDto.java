package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.company.Company;
import com.apebble.askwatson.cafe.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Getter @Setter
public class CafeDto {

    @Getter @Setter
    public static class Response {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private Location location;
        private Company company;
        private PointDto geography;
        private int reviewCount;
        private double rating;
        private boolean isEnglishPossible;

        public Response(Cafe entity) {
            this.id = entity.getId();
            this.cafeName = entity.getCafeName();
            this.cafePhoneNum = entity.getCafePhoneNum();
            this.location = entity.getLocation();
            this.company = entity.getCompany();
            this.geography = new PointDto(entity.getGeography());
            this.reviewCount = entity.getReviewCount();
            this.rating = entity.getRating();
            this.isEnglishPossible = entity.isEnglishPossible();
        }
    }

    @Getter @NoArgsConstructor
    private static class PointDto {
        private Double longitude;
        private Double latitude;

        public PointDto(Point entity) {
            this.longitude = entity.getX();
            this.latitude = entity.getY();
        }
    }
}
