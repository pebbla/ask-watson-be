package com.apebble.askwatson.cafe;

import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CafeController {
    private final CafeService cafeService;
    private final ResponseService responseService;

    // 방탈출 카페 등록
    @PostMapping(value="/admin/cafe")
    public SingleResponse<Cafe> createCafe(@ModelAttribute CafeParams params) {
        return responseService.getSingleResponse(cafeService.createCafe(params));
    }

    // 방탈출 카페 전체 조회

    // 방탈출 카페 단건 조회

    // 방탈출 카페 수정

    // 방탈출 카페 삭제

}
