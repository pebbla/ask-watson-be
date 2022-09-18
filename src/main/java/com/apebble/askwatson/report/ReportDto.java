package com.apebble.askwatson.report;

import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReportDto {

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private User reporter;
        private User reportedUser;
        private String content;
        private Review review;
        private String cafeName;
        private String themeName;
        private String createdAt;
        private Boolean handledYn;

        public Response(Report entity) {
            this.id = entity.getId();
            this.reporter = entity.getReporter();
            this.reportedUser = entity.getReportedUser();
            this.content = entity.getContent();
            this.review = entity.getReview();
            this.cafeName = entity.getReview().getTheme().getCafe().getCafeName();
            this.themeName = entity.getReview().getTheme().getThemeName();
            this.createdAt = entity.getCreatedAt().toString();
            this.handledYn = entity.isHandledYn();
        }
    }
}
