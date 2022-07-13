package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.company.Company;
import com.apebble.askwatson.cafe.location.Location;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CafeDto {

    @Getter @Setter
    public static class Response {
        private Long id;
        private String cafeName;
        private String cafePhoneNum;
        private Location location;
        private Company company;

        public Response(Cafe entity) {
            this.id = entity.getId();
            this.cafeName = entity.getCafeName();
            this.cafePhoneNum = entity.getCafePhoneNum();
            this.location = entity.getLocation();
            this.company = entity.getCompany();
        }
    }
}
