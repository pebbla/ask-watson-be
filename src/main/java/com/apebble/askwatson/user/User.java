package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.comm.util.DateConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // pk

    private String userNickname;        // 회원 별명

    private String userPhoneNum;        // 회원 전화번호

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate userBirth;        // 회원 생일

    private char userGender;            // 회원 성별 (F:여, M:남)

    private Boolean marketingAgreeYn;   // 마케팅 수신 동의 여부

    public void update(UserParams params) {
        this.userNickname = params.getUserNickname();
        this.userPhoneNum = params.getUserPhoneNum();
        this.userBirth = DateConverter.strToLocalDate(params.getUserBirth());
        this.marketingAgreeYn = params.getMarketingAgreeYn();
    }
}

