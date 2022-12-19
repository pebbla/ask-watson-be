package com.apebble.askwatson.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    @Getter @Setter
    public static class Response {
        private Long id;
        private String userNickname;
        private String userPhoneNum;
        private String userBirth;
        private char userGender;
        private Boolean marketingAgreeYn;
        private int reportedCount;
        private int reviewCount;
        private int checkCount;
        private String createdAt;

        public Response(User entity, int reportedCount, int reviewCount, int checkCount) {
            this.id = entity.getId();
            this.userNickname = entity.getUserNickname();
            this.userPhoneNum = entity.getUserPhoneNum();
            this.userBirth = entity.getUserBirth().toString();
            this.userGender = entity.getUserGender();
            this.marketingAgreeYn = entity.getMarketingAgreeYn();
            this.reportedCount = reportedCount;
            this.reviewCount = reviewCount;
            this.checkCount = checkCount;
            this.createdAt = entity.getCreatedAt().toString();
        }
    }
}
