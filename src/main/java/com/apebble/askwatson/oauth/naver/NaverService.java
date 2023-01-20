package com.apebble.askwatson.oauth.naver;


import com.apebble.askwatson.comm.exception.NaverSigninException;
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
public class NaverService {

    private final UserRepository userRepository;
    private final Gson gson;

    /**
     * 네이버토큰으로 로그인 및 회원가입
     */
    public SignInResponse signInByNaverToken(OAuthAccessToken token) {
        NaverProfile userProfile;

        try {
            userProfile = getUserProfileFromNaver(token.getAccessToken());
        } catch (Exception e) {
            throw new NaverSigninException();
        }

        try {
            User user = findUser(userProfile.getEmail());
            return makeResponse(user, false);
        } catch (UserNotFoundException e) {
            User newUser = createUser(userProfile);
            return makeResponse(newUser, true);
        }
    }

    private NaverProfile getUserProfileFromNaver(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> restRequest = setHeader(accessToken);
        URI uri = URI.create("https://openapi.naver.com/v1/nid/me");
        ResponseEntity<String> response = restTemplate.postForEntity(uri, restRequest, String.class);
        return gson.fromJson(response.getBody(), NaverProfile.class);
    }

    private HttpEntity<MultiValueMap<String, String>> setHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+ accessToken);
        return new HttpEntity<>(null, headers);
    }

    private User findUser(String email) {
        User findUser = userRepository.findByUserEmail(email).orElseThrow(UserNotFoundException::new);
        if(findUser.getPlatform() != SignInPlatform.NAVER) {
            throw new SignInPlatformNotEqualException(SignInPlatform.NAVER.getValue());
        }
        return findUser;
    }

    private User createUser(NaverProfile profile) {
        return userRepository.save(
                User.create(
                        profile.getEmail(),
                        profile.getNickName(),
                        profile.getGender(),
                        profile.getBirth(),
                        SignInPlatform.NAVER));
    }

    private SignInResponse makeResponse(User user, boolean isSignUp) {
        return new SignInResponse(user, "accessToken", "refreshToken", isSignUp);
    }

}