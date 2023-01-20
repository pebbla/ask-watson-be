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
        private String nickname;
        private Character gender;
        private String email;
        private String birthday;
        private String birthyear;
    }

    public String getEmail(){
        return this.response.email;
    }

    public String getNickName(){
        return this.response.nickname;
    }

    public Character getGender(){
        return this.response.gender;
    }

    public String getBirth(){
        return this.response.birthyear + "-" + this.response.birthday;
    }
}