package com.apebble.askwatson.theme;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ThemeController {
    private final ThemeService themeService;
    private final ResponseService responseService;

    // 방탈출 테마 등록
    @PostMapping(value="/admin/cafe/{cafeId}/theme")
    public SingleResponse<ThemeDto.Response> createTheme(@PathVariable Long cafeId, @ModelAttribute ThemeParams params) {
        return responseService.getSingleResponse(new ThemeDto.Response(themeService.createTheme(cafeId, params)));
    }

    // 테마 목록 전체 조회
    @GetMapping(value = "/themes")
    public ListResponse<ThemeDto.Response> getAllThemes() {
        List<Theme> themes = themeService.getAllThemes();
        List<ThemeDto.Response> res = new ArrayList<>();
        themes.forEach(theme -> {
            ThemeDto.Response themeDto = new ThemeDto.Response(theme);
            res.add(themeDto);
        });
        return responseService.getListResponse(res);
    }

    // 테마 단건 조회
    @GetMapping(value = "/theme/{themeId}")
    public SingleResponse<ThemeDto.Response> getTheme(@PathVariable Long themeId) {
        return responseService.getSingleResponse(new ThemeDto.Response(themeService.getOneTheme(themeId)));
    }

    // 카페별 테마 조회
    @GetMapping(value = "/cafe/{cafeId}/themes")
    public ListResponse<Theme> getThemesByCafe(@PathVariable Long cafeId) {
//        List<Theme> themes = themeService.getThemesByCafe(cafeId);
//        List<ThemeDto.Response> res = new ArrayList<>();
//        themes.forEach(theme -> {
//            ThemeDto.Response themeDto = new ThemeDto.Response(theme);
//            res.add(themeDto);
//        });
//        return responseService.getListResponse(res);
        return responseService.getListResponse(themeService.getThemesByCafe(cafeId));
    }

    // 테마 수정
    @PutMapping(value = "/theme/{themeId}")
    public SingleResponse<ThemeDto.Response> modifyTheme(@PathVariable Long themeId, @ModelAttribute ThemeParams params) {
        return responseService.getSingleResponse(new ThemeDto.Response(themeService.modifyTheme(themeId, params)));
    }

    // 테마 삭제
    @DeleteMapping(value = "/theme/{themeId}")
    public CommonResponse deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return responseService.getSuccessResponse();
    }
}
