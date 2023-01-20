package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.comm.util.DateConverter;
import com.apebble.askwatson.oauth.SignInDto;
import com.apebble.askwatson.oauth.SignInPlatform;
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
    private String userEmail;           // 회원 전화번호
    private Character userGender;       // 회원 성별 (F:여, M:남)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate userBirth;        // 회원 생일
    private Boolean marketingAgreeYn;   // 마케팅 수신 동의 여부
    @Column(nullable = false, length=6) @Enumerated(EnumType.STRING)
    private SignInPlatform platform;    // 회원 가입 플랫폼


    //==생성 메서드==//
    public static User create(SignInDto.Request params) {
        User user = new User();
        user.userEmail = params.getEmail();
        user.userNickname = !params.getNickname().isEmpty()
                ? params.getNickname()
                : params.getEmail().substring(0, params.getEmail().indexOf('@'));
        if(params.getGender() != null) user.userGender = params.getGender();
        if(params.getBirth() != null) user.userBirth = DateConverter.strToLocalDate(params.getBirth());
        if(params.getPlatform() != null) user.platform = params.getPlatform();
        return user;
    }


    //==수정 로직==//
    public void update(UserDto.Request params) {
        this.userNickname = params.getUserNickname();
        this.userGender = params.getUserGender();
        this.userBirth = DateConverter.strToLocalDate(params.getUserBirth());
        this.marketingAgreeYn = params.getMarketingAgreeYn();
    }

}
