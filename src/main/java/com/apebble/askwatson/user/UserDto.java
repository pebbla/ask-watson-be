package com.apebble.askwatson.user;

import lombok.Data;

@Data
public class UserDto {

    @Data
    public static class Request {
        private String userNickname;
        private String userPhoneNum;
        private String userBirth;
        private char userGender;
        private Boolean marketingAgreeYn;
    }

    @Data
    public static class Response {
        private Long id;
        private String userNickname;
        private String userPhoneNum;
        private String userBirth;
        private char userGender;
        private String accessToken;
        private String refreshToken;

        public Response(User entity) {
            this.id = entity.getId();
            this.userNickname = entity.getUserNickname();
            this.userPhoneNum = entity.getUserPhoneNum();
            this.userBirth = entity.getUserBirth().toString();
            this.userGender = entity.getUserGender();
            this.accessToken = "accessToken";
            this.refreshToken = "refreshToken";
        }
    }
}
