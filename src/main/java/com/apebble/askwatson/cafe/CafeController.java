package com.apebble.askwatson.cafe;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"카페"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CafeController {
    private final CafeService cafeService;
    private final ResponseService responseService;

    // 방탈출 카페 등록
    @PostMapping(value="/admin/cafes")
    public SingleResponse<CafeDto.Response> createCafe(@ModelAttribute CafeParams params) throws ParseException {
        return responseService.getSingleResponse(cafeService.createCafe(params));
    }

    // 방탈출 카페 전체 조회
    @GetMapping(value="/cafes")
    public PageResponse<CafeDto.Response> getCafes(
            @ModelAttribute CafeSearchOptions searchOptions, @PageableDefault(size=20) Pageable pageable) {
        return responseService.getPageResponse(cafeService.getCafes(searchOptions, pageable));
    }

    // 방탈출 카페 전체 조회(리스트 - 관리자웹 개발용)
    @GetMapping(value="/admin/cafes")
    public ListResponse<CafeDto.Response> getCafeList(@ModelAttribute CafeSearchOptions searchOptions) {
        return responseService.getListResponse(cafeService.getCafeList(searchOptions));
    }

    // 방탈출 카페 단건 조회
    @GetMapping(value = "/cafes/{cafeId}")
    public SingleResponse<CafeDto.Response> getCafe(@PathVariable Long cafeId) {
        return responseService.getSingleResponse(cafeService.getOneCafe(cafeId));
    }

    // 방탈출 카페 수정
    @PutMapping(value = "/admin/cafes/{cafeId}")
    public SingleResponse<CafeDto.Response> modifyCafe(@PathVariable Long cafeId, @ModelAttribute CafeParams params) throws ParseException {
        return responseService.getSingleResponse(cafeService.modifyCafe(cafeId, params));
    }

    // 방탈출 카페 삭제
    @DeleteMapping(value = "/admin/cafes/{cafeId}")
    public CommonResponse deleteCafe(@PathVariable Long cafeId) {
        cafeService.deleteCafe(cafeId);
        return responseService.getSuccessResponse();
    }
}
