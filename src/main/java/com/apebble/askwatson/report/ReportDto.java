package com.apebble.askwatson.report;

import com.apebble.askwatson.user.User;
import lombok.Data;

@Data
public class ReportDto {

    @Data
    public static class Request {
        private String content;
    }

    @Data
    public static class Response {
        private Long id;
        private UserDto reporter;
        private UserDto reportedUser;
        private String content;
        private String reviewContent;
        private Long reviewId=null;
        private String cafeName=null;
        private String themeName=null;
        private String createdAt;
        private Boolean handledYn;

        public Response(Report entity) {
            this.id = entity.getId();
            this.reporter = new UserDto(entity.getReporter());
            this.reportedUser = new UserDto(entity.getReportedUser());
            this.content = entity.getContent();
            this.reviewContent = entity.getReviewContent();
            if(!entity.isReviewNull()){
                this.reviewId = entity.getReview().getId();
                this.cafeName = entity.getReview().getTheme().getCafe().getCafeName();
                this.themeName = entity.getReview().getTheme().getThemeName();
            }
            this.createdAt = entity.getCreatedAt().toString();
            this.handledYn = entity.isHandledYn();
        }
    }

    @Data
    private static class UserDto {
        private Long id;
        private String userNickname;

        public UserDto(User entity) {
            this.id = entity.getId();
            this.userNickname = entity.getUserNickname();
        }
    }
}
