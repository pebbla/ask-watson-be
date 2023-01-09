package com.apebble.askwatson.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class UserQueryDto {

    @Data
    public static class Response {
        private Long id;
        private String userNickname;
        private String userPhoneNum;
        private String userBirth;
        private char userGender;
        private Boolean marketingAgreeYn;
        private String createdAt;
        private Long reportedCount;
        private Long reviewCount;
        private Long checkCount;

        @QueryProjection
        public Response(
                Long id,
                String userNickname,
                String userPhoneNum,
                String userBirth,
                char userGender,
                Boolean marketingAgreeYn,
                String createdAt,
                Long reportedCount,
                Long reviewCount,
                Long checkCount
        ) {
            this.id = id;
            this.userNickname = userNickname;
            this.userPhoneNum = userPhoneNum;
            this.userBirth = userBirth;
            this.userGender = userGender;
            this.marketingAgreeYn = marketingAgreeYn;
            this.createdAt = createdAt;
            this.reportedCount = reportedCount;
            this.reviewCount = reviewCount;
            this.checkCount = checkCount;
        }
    }
}
