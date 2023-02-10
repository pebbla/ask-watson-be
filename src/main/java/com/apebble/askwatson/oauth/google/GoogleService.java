package com.apebble.askwatson.oauth.google;


import com.apebble.askwatson.comm.exception.GoogleSigninException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.oauth.*;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserRepository;
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
public class GoogleService extends OAuthService {

    private final Gson gson;


    /**
     * 구글토큰으로 로그인 및 회원가입
     */
    public SignInResponse signInByGoogleToken(OAuthAccessToken token) {
        GoogleProfile googleUser;

        try {
            googleUser = getUserProfileFromGoogle(token.getAccessToken());
        } catch (Exception e) {
            throw new GoogleSigninException();
        }

        try {
            User user = findUser(googleUser.getEmail(), SignInPlatform.GOOGLE);
            return makeResponse(user, false);
        } catch (UserNotFoundException e) {
            User newUser = createUser(new UserProfile(googleUser));
            return makeResponse(newUser, true);
        }
    }

    private GoogleProfile getUserProfileFromGoogle(String accessToken) {
        ResponseEntity<String> response = getUserProfile(accessToken, "https://www.googleapis.com/oauth2/v1/tokeninfo");
        return gson.fromJson(response.getBody(), GoogleProfile.class);
    }

}