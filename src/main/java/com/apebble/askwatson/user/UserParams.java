package com.apebble.askwatson.user;

import lombok.Data;

@Data
public class UserParams {
    private String userNickname;

    private String userPhoneNum;

    private String userBirth;

    private char userGender;

    private Boolean marketingAgreeYn;
}
