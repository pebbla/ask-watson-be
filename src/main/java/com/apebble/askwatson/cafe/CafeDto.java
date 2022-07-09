package com.apebble.askwatson.cafe;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CafeDto {

    @Getter @Setter
    public static class Response {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private String locationSort;
        private String company;

        public Response(Cafe entity) {
            this.id = entity.getId();
            this.cafeName = entity.getCafeName();
            this.cafePhoneNum = entity.getCafePhoneNum();
            this.locationSort = entity.getLocationSort();
            this.company = entity.getCompany();
        }
    }
}
