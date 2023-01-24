package com.apebble.askwatson.oauth;

import com.apebble.askwatson.oauth.google.GoogleProfile;
import com.apebble.askwatson.oauth.kakao.KakaoProfile;
import com.apebble.askwatson.oauth.naver.NaverProfile;
import lombok.Data;

@Data
public class UserProfile {
    private String email;
    private String nickname;
    private Character gender;
    private String birth;
    private SignInPlatform platform;


    /**
     * 카카오 프로필 -> 유저 프로필 전환
     */
    public UserProfile(KakaoProfile kakaoProfile) {
        this.email = kakaoProfile.getEmail();
        this.nickname = !kakaoProfile.isNicknameNull() ? kakaoProfile.getNickName() : "";
        this.gender = !kakaoProfile.isGenderNull()
                ? kakaoProfile.getGender().equals("female") ? 'F' : 'M'
                : null;
        this.birth = !kakaoProfile.isBirthdayNull()
                ? "0000-" + kakaoProfile.getBirthday().substring(0,2) + "-" + kakaoProfile.getBirthday().substring(2,4)
                : null;
        this.platform = SignInPlatform.KAKAO;
    }


    /**
     * 네이버 프로필 -> 유저 프로필 전환
     */
    public UserProfile(NaverProfile naverProfile) {
        this.email = naverProfile.getEmail();
        this.nickname = !naverProfile.isNicknameNull() ? naverProfile.getNickname() : "" ;
        this.gender = !naverProfile.isGenderNull() ? naverProfile.getGender() : null;
        this.birth = !naverProfile.isBirthYearOrDayNull()
                ? naverProfile.getBirthyear() + "-" + naverProfile.getBirthday()
                : null;
        this.platform = SignInPlatform.NAVER;
    }


    /**
     * 구글 프로필 -> 유저 프로필 전환
     */
    public UserProfile(GoogleProfile googleProfile) {
        this.email = googleProfile.getEmail();
        this.nickname = "";
        this.gender = null;
        this.birth = null;
        this.platform = SignInPlatform.GOOGLE;
    }
}
