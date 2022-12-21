package com.apebble.askwatson.faq;

import lombok.Data;


@Data
public class FaqDto {

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

        public Response(Faq entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.content = entity.getContent();
        }
    }
}
