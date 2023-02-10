package com.apebble.askwatson.oauth.naver;

import lombok.Data;

@Data
public class NaverProfile {
    private String resultcode;
    private String message;
    private NaverResponse response;

    @Data
    public static class NaverResponse {
        private String id;
        private String email;
        private String nickname;
        private Character gender;
        private String birthyear;
        private String birthday;
    }

    public String getEmail(){
        return this.response.email;
    }

    public String getNickname(){
        return this.response.nickname;
    }

    public Character getGender(){
        return this.response.gender;
    }

    public String getBirthyear(){
        return this.response.birthyear;
    }

    public String getBirthday(){
        return this.response.birthday;
    }

    public boolean isNicknameNull() {
        return this.response.nickname == null;
    }

    public boolean isGenderNull() {
        return this.response.gender == null;
    }

    public boolean isBirthYearOrDayNull() {
        return this.response.birthyear == null || this.response.birthday == null;
    }
}