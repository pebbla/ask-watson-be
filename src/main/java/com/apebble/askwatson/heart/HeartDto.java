package com.apebble.askwatson.heart;

import com.apebble.askwatson.theme.ThemeDto;

import lombok.Data;

@Data
public class HeartDto {

    @Data
    public static class Response {
        private Long id;
        private ThemeDto.Response themeDto;

        public Response(Heart entity) {
            this.id = entity.getId();
            this.themeDto = new ThemeDto.Response(entity.getTheme());
        }
    }
}
