package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.comm.util.DateConverter;
import com.apebble.askwatson.escapecomplete.EscapeCompleteJpaRepository;
import com.apebble.askwatson.report.ReportJpaRepository;
import com.apebble.askwatson.review.ReviewJpaRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final ReportJpaRepository reportJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
    private final EscapeCompleteJpaRepository escapeCompleteJpaRepository;

    // 카카오 토큰으로 로그인
    public Map<String,Object> signInByKakaoToken(String access_token) {
        String phoneNum = "";
        Map<String,Object> resultMap = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
         try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // header
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            System.out.println(access_token);
            
            // 성공 : 200, 인증 실패 401
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String br_line = "";
            String result = "";

            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }
            System.out.println("response:" + result);

            JsonElement element = JsonParser.parseString(result);
            System.out.println("element:: " + element);
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            // TODO : 카카오 비즈니스 앱 등록 후 email -> phone num
            phoneNum = kakaoAccount.getAsJsonObject().get("email").getAsString();
            resultMap.put("phoneNum", phoneNum);
            System.out.println(phoneNum);

        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = userJpaRepository.findByUserPhoneNum(phoneNum).orElseThrow(UserNotFoundException::new);
        resultMap.put("access_token", "access_token");
        resultMap.put("refresh_token", "refresh_token");
        return resultMap;
    }

    // 회원 등록
    public User createUser(UserParams params) {
        User user = User.builder()
                .userNickname(params.getUserNickname())
                .userPhoneNum(params.getUserPhoneNum())
                .userBirth(DateConverter.strToLocalDate(params.getUserBirth()))
                .userGender(params.getUserGender())
                .marketingAgreeYn(params.getMarketingAgreeYn())
                .build();

        return userJpaRepository.save(user);
    }

    // 회원 전체 조회
    public Page<UserDto.Response> getAllUsers(Pageable pageable) {
        Page<User> users = userJpaRepository.findAll(pageable);


        return convertToUserDtoPage(users);
    }

    private Page<UserDto.Response> convertToUserDtoPage(Page<User> users){
        return users.map(user ->
                new UserDto.Response(user, getUserReportedCount(user), getUserReviewCount(user), getUserEscapeCompleteCount(user)));
    }

    private int getUserReportedCount(User user) {
        return reportJpaRepository.countByReportedUser(user);
    }

    private int getUserReviewCount(User user) {
        return reviewJpaRepository.countByUser(user);
    }

    private int getUserEscapeCompleteCount(User user) {
        return escapeCompleteJpaRepository.countByUser(user);
    }

    // 회원정보 수정
    public User modifyUser(Long userId, UserParams params) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(params);

        return user;
    }

    // 회원 삭제
    public void deleteUser(Long userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userJpaRepository.delete(user);
    }
}
