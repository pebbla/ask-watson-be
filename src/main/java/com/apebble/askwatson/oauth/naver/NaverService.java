package com.apebble.askwatson.oauth.naver;


import com.apebble.askwatson.comm.exception.NaverSigninException;
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
public class NaverService extends OAuthService {

    private final Gson gson;

    /**
     * 네이버토큰으로 로그인 및 회원가입
     */
    public SignInResponse signInByNaverToken(OAuthAccessToken token) {
        NaverProfile naverUser;

        try {
            naverUser = getUserProfileFromNaver(token.getAccessToken());
        } catch (Exception e) {
            throw new NaverSigninException();
        }

        try {
            User user = findUser(naverUser.getEmail(), SignInPlatform.NAVER);
            return makeResponse(user, false);
        } catch (UserNotFoundException e) {
            User newUser = createUser(new UserProfile(naverUser));
            return makeResponse(newUser, true);
        }
    }

    private NaverProfile getUserProfileFromNaver(String accessToken) {
        ResponseEntity<String> response = getUserProfile(accessToken, "https://openapi.naver.com/v1/nid/me");
        return gson.fromJson(response.getBody(), NaverProfile.class);
    }

}