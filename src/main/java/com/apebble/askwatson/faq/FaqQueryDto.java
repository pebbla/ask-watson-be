package com.apebble.askwatson.faq;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class FaqQueryDto {

    @Data
    public static class Request {
        private String title;
        private String content;
    }

    @Data
    public static class Response {
        private Long id;
        private String title;
        private String content;

        @QueryProjection
        public Response(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
    }
}
