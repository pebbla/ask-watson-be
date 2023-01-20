package com.apebble.askwatson.oauth.kakao;


import com.apebble.askwatson.comm.exception.KakaoSigninException;
import com.apebble.askwatson.comm.exception.SignInPlatformNotEqualException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.oauth.SignInDto;
import com.apebble.askwatson.oauth.SignInPlatform;
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
public class KakaoService {
    private final UserRepository userRepository;
    private final Gson gson;

    /**
     * 카카오토큰으로 로그인 및 회원가입
     */
    public SignInDto.Response signInByKakaoToken(KakaoAccessToken token) {
        KakaoProfile userProfile;

        try {
            userProfile = getUserProfileFromKakao(token.getAccessToken());
        } catch (Exception e) {
            throw new KakaoSigninException();
        }

        try {
            User user = findUser(userProfile.getEmail());
            return makeResponse(user, false);
        } catch (UserNotFoundException e) {
            User newUser = createUser(userProfile);
            return makeResponse(newUser, true);
        }
    }

    private KakaoProfile getUserProfileFromKakao(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> restRequest = setHeader(accessToken);
        URI uri = URI.create("https://kapi.kakao.com/v2/user/me");
        ResponseEntity<String> response = restTemplate.postForEntity(uri, restRequest, String.class); //예외처리 추가 필요?
        System.out.println(response);
        return gson.fromJson(response.getBody(), KakaoProfile.class);
    }

    private HttpEntity<MultiValueMap<String, String>> setHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+ accessToken);
        return new HttpEntity<>(null, headers);
    }

    private User findUser(String email) {
        User findUser = userRepository.findByUserEmail(email).orElseThrow(UserNotFoundException::new);
        if(findUser.getPlatform() != SignInPlatform.KAKAO) {
            throw new SignInPlatformNotEqualException(SignInPlatform.KAKAO.getValue());
        }
        return findUser;
    }

    private User createUser(KakaoProfile profile) {
        String nickname=null, birth=null;
        Character gender=null;

        if(profile.hasNickname()) nickname = profile.getNickName();
        if(profile.hasBirth()) birth = profile.getBirth();
        if(profile.hasGender()) gender = (profile.getGender().equals("female")) ? 'F' : 'M';

        SignInDto.Request signInParams = new SignInDto.Request(
                profile.getEmail(), nickname, gender, birth, SignInPlatform.KAKAO);
        return userRepository.save(User.create(signInParams));
    }

    private SignInDto.Response makeResponse(User user, boolean isSignUp) {
        return new SignInDto.Response(user, "accessToken", "refreshToken", isSignUp);
    }

}