package com.apebble.askwatson.oauth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OAuthAccessToken {

    @NotNull(message = "소셜 Access Token이 비어있습니다.")
    private String accessToken;

}
