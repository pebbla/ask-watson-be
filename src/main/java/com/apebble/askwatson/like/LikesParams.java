package com.apebble.askwatson.like;

import lombok.Data;

@Data
public class LikesParams {

    private Long id;                // pk

    private Long userId;            // 유저 아이디

    private Long themeId;           // 테마 아이디

}
