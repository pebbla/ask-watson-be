package com.apebble.askwatson.oauth;

import com.apebble.askwatson.user.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignInResponse {
    private Boolean isNew;
    private String accessToken;
    private String refreshToken;
    private UserDto user;

    public SignInResponse(User user, String accessToken, String refreshToken, Boolean isNew) {
        this.isNew = isNew;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = new UserDto(user);
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