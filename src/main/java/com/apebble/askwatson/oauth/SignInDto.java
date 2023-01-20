package com.apebble.askwatson.oauth;

import com.apebble.askwatson.user.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignInDto {

    @Data
    public static class Request {
        private String email;
        private String nickname;
        private Character gender;
        private String birth;
        private SignInPlatform platform;

        public Request(String email, String nickname, Character gender, String birth, SignInPlatform platform) {
            this.email = email;
            this.nickname = nickname;
            this.gender = gender;
            this.birth = birth;
            this.platform = platform;
        }
    }

    @Data
    public static class Response {
        private Boolean isNew;
        private String accessToken;
        private String refreshToken;
        private UserDto user;

        public Response(User user, String accessToken, String refreshToken, Boolean isNew) {
            this.isNew = isNew;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.user = new UserDto(user);
        }
    }

    @Data
    private static class UserDto {
        private Long id;
        private String email;
        private String nickname;
        private char gender;
        private LocalDate userBirth;

        public UserDto(User entity) {
            this.id = entity.getId();
            this.email = entity.getUserEmail();
            this.nickname = entity.getUserNickname();
            this.gender = entity.getUserGender();
            this.userBirth =entity.getUserBirth();
        }
    }
}