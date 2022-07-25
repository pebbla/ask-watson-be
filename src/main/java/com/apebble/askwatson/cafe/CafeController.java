package com.apebble.askwatson.cafe;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
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
    public ListResponse<CafeDto.Response> getCafes(@RequestParam(required = false) Long locationId, @RequestParam(required = false) Long companyId) {
        return responseService.getListResponse(cafeService.getCafes(locationId, companyId));
    }

    // 방탈출 카페 단건 조회
    @GetMapping(value = "/cafes/{cafeId}")
    public SingleResponse<CafeDto.Response> getCafe(@PathVariable Long cafeId) {
        return responseService.getSingleResponse(cafeService.getOneCafe(cafeId));
    }

    // 방탈출 카페 수정
    @PutMapping(value = "/cafes/{cafeId}")
    public SingleResponse<CafeDto.Response> modifyCafe(@PathVariable Long cafeId, @ModelAttribute CafeParams params) {
        return responseService.getSingleResponse(cafeService.modifyCafe(cafeId, params));
    }

    // 방탈출 카페 삭제
    @DeleteMapping(value = "/cafes/{cafeId}")
    public CommonResponse deleteCafe(@PathVariable Long cafeId) {
        cafeService.deleteCafe(cafeId);
        return responseService.getSuccessResponse();
    }
}
