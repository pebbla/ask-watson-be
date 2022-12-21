package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.comm.util.DateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // pk
    private String userNickname;        // 회원 별명
    private String userPhoneNum;        // 회원 전화번호
    private char userGender;            // 회원 성별 (F:여, M:남)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate userBirth;        // 회원 생일
    private Boolean marketingAgreeYn;   // 마케팅 수신 동의 여부


    //==생성 메서드==//
    public static User create(UserDto.Request params) {
        User user = new User();
        user.userNickname = params.getUserNickname();
        user.userPhoneNum = params.getUserPhoneNum();
        user.userBirth = DateConverter.strToLocalDate(params.getUserBirth());
        user.userGender = params.getUserGender();
        user.marketingAgreeYn = params.getMarketingAgreeYn();
        return user;
    }


    //==수정 로직==//
    public void update(UserDto.Request params) {
        this.userNickname = params.getUserNickname();
        this.userPhoneNum = params.getUserPhoneNum();
        this.userBirth = DateConverter.strToLocalDate(params.getUserBirth());
        this.marketingAgreeYn = params.getMarketingAgreeYn();
    }

}
