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
            if (entity.getEscapeCompleteDt() != null) {
                this.escapeCompleteDt = entity.getEscapeCompleteDt().toString();
            }
            this.theme = new ThemeDto.Response(entity.getTheme());
        }
    }

    @Getter @Setter
    public static class UpdateResponse {
        private Long id;
        private String escapeCompleteDt;

        public UpdateResponse(EscapeComplete entity) {
            this.id = entity.getId();
            if (entity.getEscapeCompleteDt() != null) {
                this.escapeCompleteDt = entity.getEscapeCompleteDt().toString();
            }
        }
    }
}
