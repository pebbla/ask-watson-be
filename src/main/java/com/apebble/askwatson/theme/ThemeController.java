package com.apebble.askwatson.theme;

import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ThemeController {
    private final ThemeService themeService;
    private final ResponseService responseService;

    // 방탈출 테마 등록
    @PostMapping(value="/admin/cafe/{cafeId}/theme")
    public SingleResponse<Theme> createTheme(@PathVariable Long cafeId, @ModelAttribute ThemeParams params) {
        return responseService.getSingleResponse(themeService.createTheme(cafeId, params));
    }
}
