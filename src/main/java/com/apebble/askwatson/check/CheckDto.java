package com.apebble.askwatson.check;

import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeDto;
import com.apebble.askwatson.theme.category.Category;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckDto {

    @Getter @Setter
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

    @Getter @Setter
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
