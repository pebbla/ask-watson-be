package com.apebble.askwatson.review;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ReviewQueryDto {

    @Data
    public static class Response {
        private Long id;
        private UserDto user;
        private double difficulty;
        private double timeTaken;
        private Integer usedHintNum;
        private double rating;
        private double deviceRatio;
        private double activity;
        private String content;

        @QueryProjection
        public Response(
                Long id,
                double difficulty,
                double timeTaken,
                Integer usedHintNum,
                double rating,
                double deviceRatio,
                double activity,
                String content,
                Long userId, String userNickname
        ) {
            this.id = id;
            this.difficulty = difficulty;
            this.timeTaken = timeTaken;
            this.usedHintNum = usedHintNum;
            this.rating = rating;
            this.deviceRatio = deviceRatio;
            this.activity = activity;
            this.content = content;
            this.user = new UserDto(userId, userNickname);
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
