package com.apebble.askwatson.oauth;

import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import com.apebble.askwatson.oauth.google.GoogleService;
import com.apebble.askwatson.oauth.kakao.KakaoService;
import com.apebble.askwatson.oauth.naver.NaverService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"소셜로그인"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class SignInController {

    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final GoogleService googleService;
    private final ResponseService responseService;

    // 카카오 토큰으로 로그인
    @PostMapping(value = "/signin/kakao")
    public SingleResponse<SignInResponse> signInByKakaoToken(@RequestBody OAuthAccessToken token){
        return responseService.getSingleResponse(kakaoService.signInByKakaoToken(token));
    }

    // 네이버 토큰으로 로그인
    @PostMapping(value = "/signin/naver")
    public SingleResponse<SignInResponse> signInByNaverToken(@RequestBody OAuthAccessToken token){
        return responseService.getSingleResponse(naverService.signInByNaverToken(token));
    }

    // 구글 토큰으로 로그인
    @PostMapping(value = "/signin/google")
    public SingleResponse<SignInResponse> signInByGoogleToken(@RequestBody OAuthAccessToken token){
        return responseService.getSingleResponse(googleService.signInByGoogleToken(token));
    }

}
