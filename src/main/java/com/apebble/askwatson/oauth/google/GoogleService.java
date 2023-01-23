package com.apebble.askwatson.oauth.google;


import com.apebble.askwatson.comm.exception.GoogleSigninException;
import com.apebble.askwatson.comm.exception.SignInPlatformNotEqualException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.oauth.OAuthAccessToken;
import com.apebble.askwatson.oauth.SignInPlatform;
import com.apebble.askwatson.oauth.SignInResponse;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GoogleService {

    private final UserRepository userRepository;
    private final Gson gson;


    /**
     * 구글토큰으로 로그인 및 회원가입
     */
    public SignInResponse signInByGoogleToken(OAuthAccessToken token) {
        GoogleProfile userProfile;

        try {
            userProfile = getUserProfileFromGoogle(token.getAccessToken());
        } catch (Exception e) {
            throw new GoogleSigninException();
        }

        try {
            User user = findUser(userProfile.getEmail());
            return makeResponse(user, false);
        } catch (UserNotFoundException e) {
            User newUser = createUser(userProfile);
            return makeResponse(newUser, true);
        }
    }

    private GoogleProfile getUserProfileFromGoogle(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> restRequest = setHeader(accessToken);
        URI uri = URI.create("https://www.googleapis.com/oauth2/v1/tokeninfo");
        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(uri, restRequest, String.class);
            return gson.fromJson(response.getBody(), GoogleProfile.class);
        } catch (Exception e) {
            throw new IllegalStateException(); //차후 에외처리 추가 필요
        }
    }

    private HttpEntity<MultiValueMap<String, String>> setHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+ accessToken);
        return new HttpEntity<>(null, headers);
    }

    private User findUser(String email) {
        User findUser = userRepository.findByUserEmail(email).orElseThrow(UserNotFoundException::new);
        if(findUser.getPlatform() != SignInPlatform.GOOGLE) {
            throw new SignInPlatformNotEqualException(findUser.getPlatform().getValue());
        }
        return findUser;
    }

    private User createUser(GoogleProfile profile) {
        return userRepository.save(User.create(profile.getEmail(), "", null, null, SignInPlatform.GOOGLE));
    }

    private SignInResponse makeResponse(User user, boolean isSignUp) {
        return new SignInResponse(user, "accessToken", "refreshToken", isSignUp);
    }

}