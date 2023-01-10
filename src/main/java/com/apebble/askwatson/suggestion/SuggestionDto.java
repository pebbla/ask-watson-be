package com.apebble.askwatson.suggestion;

import lombok.Data;

@Data
public class SuggestionDto {

    @Data
    public static class Request {
        private String content;
    }

    @Data
    public static class Response {
        private Long id;
        private String content;

        public Response(Suggestion entity) {
            this.id = entity.getId();
            this.content = entity.getContent();
        }
    }
}
