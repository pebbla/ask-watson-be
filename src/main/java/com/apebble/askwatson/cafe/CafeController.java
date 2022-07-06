package com.apebble.askwatson.cafe;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CafeController {
    private final CafeService cafeService;
    private final ResponseService responseService;

    // 방탈출 카페 등록
    @PostMapping(value="/admin/cafes")
    public SingleResponse<Cafe> createCafe(@ModelAttribute CafeParams params) {
        return responseService.getSingleResponse(cafeService.createCafe(params));
    }

    // 방탈출 카페 전체 조회
    @GetMapping(value="/cafes")
    public ListResponse<Cafe> getCafes() {
        return responseService.getListResponse(cafeService.getCafes());
    }

    // 방탈출 카페 단건 조회
    @GetMapping(value = "/cafes/{cafeId}")
    public SingleResponse<Cafe> getCafe(@PathVariable Long cafeId) {
        return responseService.getSingleResponse(cafeService.getOneCafe(cafeId));
    }

    // 방탈출 카페 수정
    @PutMapping(value = "/cafes/{cafeId}")
    public SingleResponse<Cafe> modifyCafe(@PathVariable Long cafeId, @ModelAttribute CafeParams params) {
        return responseService.getSingleResponse(cafeService.modifyCafe(cafeId, params));
    }

    // 방탈출 카페 삭제
    @DeleteMapping(value = "/cafes/{cafeId}")
    public CommonResponse deleteCafe(@PathVariable Long cafeId) {
        cafeService.deleteCafe(cafeId);
        return responseService.getSuccessResponse();
    }
}
