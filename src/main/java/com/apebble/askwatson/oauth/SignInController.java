package com.apebble.askwatson.oauth;

import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import com.apebble.askwatson.oauth.kakao.KakaoAccessToken;
import com.apebble.askwatson.oauth.kakao.KakaoService;
import com.apebble.askwatson.user.UserService;
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
    private final UserService userService;
    private final KakaoService kakaoService;
    private final ResponseService responseService;

    // 카카오 토큰으로 로그인
    @PostMapping(value = "/signin/kakao")
    public SingleResponse<SignInDto.Response> signInByKakaoToken(@RequestBody KakaoAccessToken token){
        return responseService.getSingleResponse(kakaoService.signInByKakaoToken(token));
    }

    // 네이버 토큰으로 로그인
//    @PostMapping(value = "/signin/naver")
//    public SingleResponse<Map<String, Object>> signInByNaverToken(@RequestBody HashMap<String, Object> map){
//        System.out.println(map.get("accessToken"));
//        return responseService.getSingleResponse(kakaoService.signInByNaverToken(map.get("accessToken").toString()));
//    }

    // 구글 토큰으로 로그인
}
