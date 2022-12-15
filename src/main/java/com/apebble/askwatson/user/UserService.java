package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.exception.ServerException;
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

        User user = userJpaRepository.findByUserPhoneNum(phoneNum).orElseThrow(UserNotFoundException::new);
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
        User user = userJpaRepository.findByUserPhoneNum(resultMap.get("phonenum").toString()).orElseThrow(UserNotFoundException::new);
        resultMap.put("access_token", "access_token");
        resultMap.put("refresh_token", "refresh_token");
        return resultMap;
    }



    /**
     * 회원 등록
     */
    public Long createUser(UserParams params) {
        return userJpaRepository.save(User.create(params)).getId();
    }

    /**
     * 회원 전체 조회
     */
    @Transactional(readOnly = true)
    public List<UserDto.Response> getAllUsers(String searchWord) {
        List<User> users = (searchWord == null)
                ? userJpaRepository.findAll()
                : userJpaRepository.findUsersBySearchWord(searchWord);

        return convertToDtoList(users);
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


    /**
     * 회원정보 수정
     */
    public void modifyUser(Long userId, UserParams params) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(params);
    }


    /**
     * 회원 삭제
     */
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
        reviews.forEach(Review::deleteUser);
    }

    private void deleteEscapeCompletesHandlingReviews(User user) {
        List<EscapeComplete> escapeCompletes = escapeCompleteJpaRepository.findByUserId(user.getId());
        setReviewsEscapeCompleteNull(user);
        escapeCompletes.forEach(escapeCompleteService::deleteEscapeComplete);
    }

    private void setReviewsEscapeCompleteNull(User user) {
        List<Review> reviews = reviewJpaRepository.findByUser(user);
        reviews.forEach(Review::deleteEscapeComplete);
    }

    private void setReportsReporterNull(User user) {
        List<Report> reports = reportJpaRepository.findByReporter(user);
        reports.forEach(Report::deleteReporter);
    }

    private void setReportsReportedUserNull(User user) {
        List<Report> reports = reportJpaRepository.findByReportedUser(user);
        reports.forEach(Report::deleteReportedUser);
    }


    //==DTO 변환 함수==//
    private List<UserDto.Response> convertToDtoList(List<User> users){
        return users.stream().map(user ->
                new UserDto.Response(user, getUserReportedCount(user), getUserReviewCount(user), getUserEscapeCompleteCount(user))
        ).collect(toList());
    }

    private UserDto.Response convertToDto(User user){
        return new UserDto.Response(user, getUserReportedCount(user), getUserReviewCount(user), getUserEscapeCompleteCount(user));
    }

}
