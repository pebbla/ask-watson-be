package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.theme.ThemeDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EscapeCompleteDto {

    @Getter @Setter
    public static class Response {
        private Long id;
        private String escapeCompleteDt;
        private ThemeDto.Response theme;

        public Response(EscapeComplete entity) {
            this.id = entity.getId();
            this.theme = new ThemeDto.Response(entity.getTheme());
        }
    }
}
