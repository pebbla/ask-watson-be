package com.apebble.askwatson.oauth.kakao;

import lombok.Data;

@Data
public class KakaoProfile {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;
    private Profile properties;

    @Data
    public static class KakaoAccount {
        private Boolean email_needs_agreement;
        private Boolean profile_nickname_needs_agreement;
        private Boolean birthday_needs_agreement;
        private Boolean gender_needs_agreement;


        private Boolean has_email;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private Boolean has_birthday;
        private Boolean has_gender;

        private String email;
        private Profile profile;
        private String birthday; //0403 -> 일단 0000-04-03으로 저장
        private String birthday_type; //SOLAR
        private String nickname;
        private String gender; //male, female
    }

    @Data
    public static class Profile {
        private String nickname;
    }

    public String getEmail(){
        return this.kakao_account.email;
    }

    public String getNickName(){
        return (properties != null)
                ? this.kakao_account.profile.nickname
                : "";
    }

    public String getGender(){
        return this.kakao_account.gender;
    }

    public String getBirthday(){
        return this.kakao_account.birthday;
    }

    public boolean isNicknameNull() {
        return properties == null;
    }

    public boolean isGenderNull() {
        return this.kakao_account.gender == null;
    }

    public boolean isBirthdayNull() {
        return this.kakao_account.birthday == null;
    }

}