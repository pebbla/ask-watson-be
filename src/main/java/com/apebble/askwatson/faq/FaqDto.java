package com.apebble.askwatson.faq;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class FaqDto {

    @Getter @Setter
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
