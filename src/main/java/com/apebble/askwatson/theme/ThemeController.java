package com.apebble.askwatson.theme;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"테마"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ThemeController {
    private final ThemeService themeService;
    private final ResponseService responseService;

    // 방탈출 테마 등록
    @PostMapping(value="/admin/cafes/{cafeId}/themes")
    public SingleResponse<Theme> createTheme(@PathVariable Long cafeId, @RequestBody ThemeParams params) {
        return responseService.getSingleResponse(themeService.createTheme(cafeId, params));
    }

    // 테마 목록 전체 조회
    @GetMapping(value = "/themes")
    public PageResponse<ThemeDto.Response> getThemes(
            @ModelAttribute ThemeSearchOptions searchOptions, @PageableDefault(size=20) Pageable pageable) {
        return responseService.getPageResponse(themeService.getThemes(searchOptions, pageable));
    }

    // 방탈출 테마 전체 조회(리스트 - 관리자웹용)
    @GetMapping(value="/admin/themes")
    public ListResponse<ThemeDto.Response> getThemeList(@RequestParam(required = false) String searchWord, @RequestParam(required = false) Boolean sortByUpdateYn) {
        return responseService.getListResponse(themeService.getThemeList(searchWord, sortByUpdateYn));
    }

    // 테마 단건 조회
    @GetMapping(value = "/themes/{themeId}")
    public SingleResponse<OneThemeDto.Response> getTheme(@PathVariable Long themeId, @RequestParam(required = false) Long userId) {
        return responseService.getSingleResponse(themeService.getOneTheme(themeId, userId));
    }

    // 카페별 테마 조회
    @GetMapping(value = "/cafes/{cafeId}/themes")
    public ListResponse<Theme> getThemesByCafe(@PathVariable Long cafeId) {
        return responseService.getListResponse(themeService.getThemesByCafe(cafeId));
    }

    // 테마 수정
    @PutMapping(value = "/admin/themes/{themeId}")
    public SingleResponse<ThemeDto.Response> modifyTheme(@PathVariable Long themeId, @RequestBody ThemeParams params) {
        return responseService.getSingleResponse(themeService.modifyTheme(themeId, params));
    }

    // 테마 삭제
    @DeleteMapping(value = "/admin/themes/{themeId}")
    public CommonResponse deleteTheme(@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return responseService.getSuccessResponse();
    }
}
