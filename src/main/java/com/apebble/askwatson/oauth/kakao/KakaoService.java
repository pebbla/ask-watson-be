package com.apebble.askwatson.oauth.kakao;


import com.apebble.askwatson.comm.exception.KakaoSigninException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.oauth.*;
import com.apebble.askwatson.user.User;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService extends OAuthService {

    private final Gson gson;

    /**
     * 카카오토큰으로 로그인 및 회원가입
     */
    public SignInResponse signInByKakaoToken(OAuthAccessToken token) {
        KakaoProfile kakaoUser;

        try {
            kakaoUser = getUserProfileFromKakao(token.getAccessToken());
        } catch (Exception e) {
            throw new KakaoSigninException();
        }

        try {
            User user = findUser(kakaoUser.getEmail(), SignInPlatform.KAKAO);
            return makeResponse(user, false);
        } catch (UserNotFoundException e) {
            User newUser = createUser(new UserProfile(kakaoUser));
            return makeResponse(newUser, true);
        }
    }

    private KakaoProfile getUserProfileFromKakao(String accessToken) {
        ResponseEntity<String> response = getUserProfile(accessToken, "https://kapi.kakao.com/v2/user/me");
        return gson.fromJson(response.getBody(), KakaoProfile.class);
    }

}