package com.apebble.askwatson.theme;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
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
    @PostMapping(value="/admin/cafes/{cafeId}/themes")
    public SingleResponse<Theme> createTheme(@PathVariable Long cafeId, @ModelAttribute ThemeParams params) {
        return responseService.getSingleResponse(themeService.createTheme(cafeId, params));
    }

    // 테마 목록 전체 조회
    @GetMapping(value = "/themes")
    public ListResponse<Theme> getAllThemes() {
        return responseService.getListResponse(themeService.getAllThemes());
    }

    // 테마 단건 조회
    @GetMapping(value = "/theme/{themeId}")
    public SingleResponse<Theme> getTheme(@PathVariable Long themeId) {
        return responseService.getSingleResponse(themeService.getOneTheme(themeId));
    }

    // 카페별 테마 조회
    @GetMapping(value = "/cafe/{cafeId}/themes")
    public ListResponse<Theme> getThemesByCafe(@PathVariable Long cafeId) {
        return responseService.getListResponse(themeService.getThemesByCafe(cafeId));
    }

    // 테마 수정
    @PutMapping(value = "/theme/{themeId}")
    public SingleResponse<Theme> modifyTheme(@PathVariable Long themeId, @ModelAttribute ThemeParams params) {
        return responseService.getSingleResponse(themeService.modifyTheme(themeId, params));
    }

    // 테마 삭제
    @DeleteMapping(value = "/theme/{themeId}")
    public CommonResponse deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return responseService.getSuccessResponse();
    }
}
