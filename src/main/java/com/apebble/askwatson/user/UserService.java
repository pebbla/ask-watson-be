package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.exception.ServerException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.check.Check;
import com.apebble.askwatson.check.CheckRepository;
import com.apebble.askwatson.check.CheckService;
import com.apebble.askwatson.report.Report;
import com.apebble.askwatson.report.ReportRepository;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.review.ReviewRepository;
import com.apebble.askwatson.suggestion.Suggestion;
import com.apebble.askwatson.suggestion.SuggestionRepository;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ReviewRepository reviewRepository;
    private final SuggestionRepository suggestionRepository;
    private final CheckRepository checkRepository;
    private final CheckService checkService;


    /**
     * 로그인(카카오)
     */
    public Map<String,Object> signInByKakaoToken(String accessToken) {
        String phoneNum = "";
        Map<String,Object> resultMap = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // header
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            System.out.println(accessToken);

            // 성공 : 200, 인증 실패 401
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String br_line = "";
            StringBuilder result = new StringBuilder();

            while ((br_line = br.readLine()) != null) {
                result.append(br_line);
            }
            System.out.println("response:" + result);

            JsonElement element = JsonParser.parseString(result.toString());
            System.out.println("element:: " + element);
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            // TODO : 카카오 비즈니스 앱 등록 후 email -> phone num
            phoneNum = kakaoAccount.getAsJsonObject().get("email").getAsString();
            resultMap.put("phoneNum", phoneNum);
            System.out.println(phoneNum);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException("kakao 로그인 시 문제 발생");
        }

        User user = userRepository.findByUserPhoneNum(phoneNum).orElseThrow(UserNotFoundException::new);
        resultMap.put("access_token", "access_token");
        resultMap.put("refresh_token", "refresh_token");
        return resultMap;
    }


    /**
     * 로그인(네이버)
     */
    public Map<String,Object> signInByNaverToken (String accessToken)  {

        Map<String,Object> resultMap = new HashMap<>();
        String reqURL = "https://openapi.naver.com/v1/nid/me";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Naver-Client-Id", "3gtvwzljbdRnobTbeKwG");
            conn.setRequestProperty("X-Naver-Client-Secret", "BbAjtY1kBe");

            // 성공 : 200, 인증 실패 401
            int responseCode = conn.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String br_line = "";
            String result = "";
            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }
            br.close();
            System.out.println("response:" + result);
            JsonElement element = JsonParser.parseString(result);
            System.out.println("element:: " + element);
            JsonObject naverAccount = element.getAsJsonObject().get("response").getAsJsonObject();
            System.out.println(naverAccount.get("mobile").toString());
            resultMap.put("phonenum", naverAccount.get("mobile"));
            System.out.println(naverAccount);

        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException("naver 로그인 시 문제 발생");
        }
        User user = userRepository.findByUserPhoneNum(resultMap.get("phonenum").toString()).orElseThrow(UserNotFoundException::new);
        resultMap.put("access_token", "access_token");
        resultMap.put("refresh_token", "refresh_token");
        return resultMap;
    }



    /**
     * 회원 등록
     */
    public Long createUser(UserDto.Request params) {
        return userRepository.save(User.create(params)).getId();
    }


    /**
     * 회원 단건 조회
     */
    @Transactional(readOnly = true)
    public User findOne(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }


    /**
     * 회원정보 수정
     */
    public void modifyUser(Long userId, UserDto.Request params) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(params);
    }


    /**
     * 회원 삭제
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        handleUserAssociations(user);
        userRepository.delete(user);
    }

    private void handleUserAssociations(User user) {
        deleteChecksHandlingReviews(user);
        setReviewsUserNull(user);
        setReportsReporterNull(user);
        setReportsReportedUserNull(user);
        setSuggestionsUserNull(user);
    }

    private void setReviewsUserNull(User user) {
        List<Review> reviews = reviewRepository.findByUser(user);
        reviews.forEach(Review::deleteUser);
    }

    private void deleteChecksHandlingReviews(User user) {
        List<Check> checks = checkRepository.findByUserId(user.getId());
        setReviewsCheckNull(user);
        checks.forEach(checkService::deleteCheck);
    }

    private void setReviewsCheckNull(User user) {
        List<Review> reviews = reviewRepository.findByUser(user);
        reviews.forEach(Review::deleteCheck);
    }

    private void setReportsReporterNull(User user) {
        List<Report> reports = reportRepository.findByReporter(user);
        reports.forEach(Report::deleteReporter);
    }

    private void setReportsReportedUserNull(User user) {
        List<Report> reports = reportRepository.findByReportedUser(user);
        reports.forEach(Report::deleteReportedUser);
    }

    private void setSuggestionsUserNull(User user) {
        List<Suggestion> suggestions = suggestionRepository.findByUser(user);
        suggestions.forEach(Suggestion::deleteUser);
    }

}
