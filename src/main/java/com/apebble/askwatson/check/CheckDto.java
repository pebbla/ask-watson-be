package com.apebble.askwatson.check;

import com.apebble.askwatson.theme.ThemeDto;
import lombok.Data;

@Data
public class CheckDto {

    @Data
    public static class Response {
        private Long id;
        private String checkDt;
        private ThemeDto.Response theme;

        public Response(Check entity) {
            this.id = entity.getId();
            if (entity.getCheckDt() != null) {
                this.checkDt = entity.getCheckDt().toString();
            }
            this.theme = new ThemeDto.Response(entity.getTheme());
        }
    }

    @Data
    public static class SimpleResponse {
        private Long id;
        private String checkDt;

        public SimpleResponse(Check entity) {
            this.id = entity.getId();
            if (entity.getCheckDt() != null) {
                this.checkDt = entity.getCheckDt().toString();
            }
        }
    }
}
