package com.apebble.askwatson.theme;
import org.springframework.http.MediaType;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"테마"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ThemeController {

    private final ThemeService themeService;
    private final ResponseService responseService;

    // 방탈출 테마 등록
    @PostMapping(value="/admin/cafes/{cafeId}/themes", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public SingleResponse<ThemeDto.Response> createTheme(@PathVariable Long cafeId, @ModelAttribute ThemeParams params, @RequestPart(value = "file", required = false) MultipartFile file) {
        return responseService.getSingleResponse(themeService.createTheme(cafeId, params, file));
    }

    // 테마 목록 전체 조회
    @GetMapping(value = "/themes")
    public PageResponse<ThemeDto.Response> getThemes(ThemeSearchOptions searchOptions, @PageableDefault(size=20) Pageable pageable) {
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
    public ListResponse<ThemeDto.Response> getThemesByCafe(@PathVariable Long cafeId) {
        return responseService.getListResponse(themeService.getThemesByCafe(cafeId));
    }

    // 테마 수정
    @PutMapping(value = "/admin/themes/{themeId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public SingleResponse<ThemeDto.Response> modifyTheme(@PathVariable Long themeId, @ModelAttribute ThemeParams params, @RequestPart(value = "file", required=false) MultipartFile file) {
        return responseService.getSingleResponse(themeService.modifyTheme(themeId, params, file));
    }

    // 테마 이용가능여부 변경
    @PatchMapping(value = "/admin/themes/{themeId}")
    public CommonResponse modifyThemeAvailability(@PathVariable Long themeId, @RequestParam boolean isAvailable) {
        themeService.modifyThemeAvailability(themeId, isAvailable);
        return responseService.getSuccessResponse();
    }

}
