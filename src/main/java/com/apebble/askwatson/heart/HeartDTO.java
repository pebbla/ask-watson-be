package com.apebble.askwatson.heart;

import com.apebble.askwatson.theme.ThemeDto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HeartDTO {

    @Getter @Setter
    public static class Response {
        private Long id;
        private ThemeDto.Response themeDto;

        public Response(Heart entity) {
            this.id = entity.getId();
            this.themeDto = new ThemeDto.Response(entity.getTheme());

        }
    }
}
