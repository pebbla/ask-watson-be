package com.apebble.askwatson.oauth;

import com.apebble.askwatson.comm.exception.SignInPlatformNotEqualException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Slf4j
public abstract class OAuthService {

    @Autowired
    protected UserRepository userRepository;


    /**
     * 소셜로그인 api 통해 유저 정보 조회
     */
    protected ResponseEntity<String> getUserProfile(String accessToken, String apiUri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> restRequest = setHeader(accessToken);
        URI uri = URI.create(apiUri);
        try {
            return restTemplate.postForEntity(uri, restRequest, String.class);
        } catch (Exception e) {
            throw new IllegalStateException(); //에외처리 추가 필요?
        }
    }

    private HttpEntity<MultiValueMap<String, String>> setHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+ accessToken);
        return new HttpEntity<>(null, headers);
    }


    /**
     * 회원 존재여부 및 로그인 플랫폼 일치여부 확인
     */
    protected User findUser(String email, SignInPlatform platform) {
        User findUser = userRepository.findByUserEmail(email).orElseThrow(UserNotFoundException::new);
        if(findUser.getPlatform() != platform) {
            throw new SignInPlatformNotEqualException(findUser.getPlatform().getValue());
        }
        return findUser;
    }


    /**
     * 소셜 회원가입 통해 회원 생성
     */
    protected User createUser(UserProfile profile) {
        return userRepository.save(
                User.create(
                        profile.getEmail(),
                        profile.getNickname(),
                        profile.getGender(),
                        profile.getBirth(),
                        profile.getPlatform()));
    }

    protected SignInResponse makeResponse(User user, boolean isSignUp) {
        return new SignInResponse(user, "accessToken", "refreshToken", isSignUp);
    }

}
