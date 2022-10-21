package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.comm.util.DateConverter;
import com.apebble.askwatson.escapecomplete.EscapeComplete;
import com.apebble.askwatson.escapecomplete.EscapeCompleteJpaRepository;
import com.apebble.askwatson.escapecomplete.EscapeCompleteService;
import com.apebble.askwatson.report.Report;
import com.apebble.askwatson.report.ReportJpaRepository;
import com.apebble.askwatson.review.Review;
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
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final ReportJpaRepository reportJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
    private final EscapeCompleteJpaRepository escapeCompleteJpaRepository;
    private final EscapeCompleteService escapeCompleteService;

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
    public List<UserDto.Response> getAllUsers(String searchWord) {
        List<User> users = (searchWord == null)
                ? userJpaRepository.findAll()
                : userJpaRepository.findUsersBySearchWord(searchWord);

        return convertToUserDtoList(users);
    }

    private List<UserDto.Response> convertToUserDtoList(List<User> users){
        return users.stream().map(user ->
                new UserDto.Response(user, getUserReportedCount(user), getUserReviewCount(user), getUserEscapeCompleteCount(user))
        ).collect(toList());
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
        handleUserAssociations(user);
        userJpaRepository.delete(user);
    }

    private void handleUserAssociations(User user) {
        deleteEscapeCompletesHandlingReviews(user);
        setReviewsUserNull(user);
        setReportsReporterNull(user);
        setReportsReportedUserNull(user);
    }

    private void setReviewsUserNull(User user) {
        List<Review> reviews = reviewJpaRepository.findByUser(user);
        reviews.forEach(review -> review.setUser(null));
    }

    private void deleteEscapeCompletesHandlingReviews(User user) {
        List<EscapeComplete> escapeCompletes = escapeCompleteJpaRepository.findByUserId(user.getId());
        setReviewsEscapeCompleteNull(user);
        escapeCompletes.forEach(escapeCompleteService::deleteEscapeComplete);
    }

    private void setReviewsEscapeCompleteNull(User user) {
        List<Review> reviews = reviewJpaRepository.findByUser(user);
        reviews.forEach(review -> review.setEscapeComplete(null));
    }

    private void setReportsReporterNull(User user) {
        List<Report> reports = reportJpaRepository.findByReporter(user);
        reports.forEach(report -> report.setReporter(null));
    }

    private void setReportsReportedUserNull(User user) {
        List<Report> reports = reportJpaRepository.findByReportedUser(user);
        reports.forEach(report -> report.setReportedUser(null));
    }
}
