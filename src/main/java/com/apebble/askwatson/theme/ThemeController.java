package com.apebble.askwatson.theme;
import com.apebble.askwatson.check.Check;
import com.apebble.askwatson.heart.Heart;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(tags = {"테마"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ThemeController {

    private final ThemeService themeService;
    private final ResponseService responseService;

    // 방탈출 테마 등록
    @PostMapping(value="/admin/cafes/{cafeId}/themes", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public SingleResponse<ThemeDto.Response> createTheme(@PathVariable Long cafeId,
                                                         @RequestPart ThemeDto.Request params,
                                                         @RequestPart(value = "file", required = false) MultipartFile file) {
        Long themeId = themeService.createTheme(cafeId, params, file);
        return responseService.getSingleResponse(new ThemeDto.Response(themeService.findOne(themeId)));
    }

    // 테마 목록 전체 조회
    @GetMapping(value = "/themes")
    public PageResponse<ThemeDto.Response> getThemes(ThemeSearchOptions searchOptions,
                                                     @PageableDefault(size=20) Pageable pageable) {
        return responseService.getPageResponse(toDtoPage(themeService.getThemes(searchOptions, pageable)));
    }

    // 방탈출 테마 전체 조회(리스트 - 관리자웹용)
    @GetMapping(value="/admin/themes")
    public ListResponse<ThemeDto.Response> getThemeList(@RequestParam(required = false) String searchWord,
                                                        @RequestParam(required = false) Boolean sortByUpdateYn) {
        return responseService.getListResponse(toDtoList(themeService.getThemeList(searchWord, sortByUpdateYn)));
    }

    // 테마 단건 조회
    @GetMapping(value = "/themes/{themeId}")
    public SingleResponse<OneThemeDto.Response> getTheme(@PathVariable Long themeId,
                                                         @RequestParam(required = false) Long userId) {
        return responseService.getSingleResponse(themeService.getOneThemeWithUserHearted(themeId, userId));
    }

    // 카페별 테마 조회
    @GetMapping(value = "/cafes/{cafeId}/themes")
    public ListResponse<ThemeDto.Response> getThemesByCafe(@PathVariable Long cafeId) {
        return responseService.getListResponse(toDtoList(themeService.getThemesByCafe(cafeId)));
    }

    // 테마 수정
    @PutMapping(value = "/admin/themes/{themeId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public SingleResponse<ThemeDto.Response> modifyTheme(@PathVariable Long themeId,
                                                         @RequestPart ThemeDto.Request params,
                                                         @RequestPart(value = "file", required=false) MultipartFile file) {
        themeService.modifyTheme(themeId, params, file);
        return responseService.getSingleResponse(new ThemeDto.Response(themeService.findOne(themeId)));
    }

    // 테마 이용가능여부 변경
    @PatchMapping(value = "/admin/themes/{themeId}")
    public CommonResponse modifyThemeAvailability(@PathVariable Long themeId, @RequestParam boolean isAvailable) {
        themeService.modifyThemeAvailability(themeId, isAvailable);
        return responseService.getSuccessResponse();
    }

    //==DTO 변환 메서드==//
    private Page<ThemeDto.Response> toDtoPage(Page<Theme> themeList){
        return themeList.map(ThemeDto.Response::new);
    }

    private List<ThemeDto.Response> toDtoList(List<Theme> themeList){
        return themeList.stream().map(ThemeDto.Response::new).collect(toList());
    }

}
