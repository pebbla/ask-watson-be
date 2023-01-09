package com.apebble.askwatson.report;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ReportQueryDto {

    @Data
    public static class Response {
        private Long id;
        private String content;
        private String reviewContent;
        private Long reviewId;
        private String cafeName;
        private String themeName;
        private String createdAt;
        private Boolean handledYn;
        private UserDto reporter;
        private UserDto reportedUser;

        @QueryProjection
        public Response(
                Long id,
                String content,
                String reviewContent,
                Long reviewId,
                String cafeName,
                String themeName,
                String createdAt,
                Boolean handledYn,
                Long reporterId, String reporterNickname,
                Long reportedUserId, String reportedUserNickname
        ) {
            this.id = id;
            this.content = content;
            this.reviewContent = reviewContent;
            this.reviewId = reviewId;
            this.cafeName = cafeName;
            this.themeName = themeName;
            this.createdAt = createdAt;
            this.handledYn =handledYn;
            this.reporter = new UserDto(reporterId, reporterNickname);
            this.reportedUser = new UserDto(reportedUserId, reportedUserNickname);
        }
    }

    @Data
    private static class UserDto {
        private Long id;
        private String userNickname;

        public UserDto(Long id, String userNickname) {
            this.id = id;
            this.userNickname = userNickname;
        }
    }
}
