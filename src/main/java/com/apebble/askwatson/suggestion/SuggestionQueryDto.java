package com.apebble.askwatson.suggestion;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SuggestionQueryDto {

    @Data
    public static class Response {
        private Long id;
        private String content;
        private String createdAt;
        private Boolean handledYn;
        private Long cafeId;
        private String cafeName;
        private Long userId;
        private String userNickname;

        @QueryProjection
        public Response(
                Long id,
                String content,
                String createdAt,
                Boolean handledYn,
                Long cafeId, String cafeName,
                Long userId, String userNickname
        ) {
            this.id = id;
            this.content = content;
            this.createdAt = createdAt;
            this.handledYn =handledYn;
            this.cafeId = cafeId;
            this.cafeName = cafeName;
            this.userId = userId;
            this.userNickname = userNickname;
        }
    }
}
