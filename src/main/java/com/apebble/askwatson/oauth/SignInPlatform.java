package com.apebble.askwatson.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignInPlatform {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER"),
    APPLE("APPLE");
    private final String value;
}
