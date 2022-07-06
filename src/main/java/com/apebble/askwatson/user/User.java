package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // pk

    private String userPassword;        // 회원 비밀번호

    private String userName;            // 회원 이름

    private String userNickName;        // 회원 별명

    private String userPhoneNum;        // 회원 전화번호

    private Date userBirth;             // 회원 생일

    private char userGender;            // 회원 성별 (F:여, M:남)

    private Boolean marketingAgreeYn;   // 마케팅 수신 동의 여부
}

