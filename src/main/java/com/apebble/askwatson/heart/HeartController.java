package com.apebble.askwatson.heart;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;


@Api(tags = {"좋아요"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class HeartController {

    private final HeartService heartService;
    private final ResponseService responseService;

    // 좋아요 등록
    @PostMapping(value="/user/{userId}/themes/{themeId}/hearts")
    public SingleResponse<Long> createHeart(@PathVariable Long userId, @PathVariable Long themeId) {
        return responseService.getSingleResponse(heartService.createHeart(userId, themeId));
    }

    // 좋아요 해제
    @DeleteMapping(value = "/user/hearts/{heartId}")
    public CommonResponse deleteHeart(@PathVariable Long heartId){
        heartService.deleteHeart(heartId);
        return responseService.getSuccessResponse();
    }

    // 좋아요 목록
    @GetMapping(value="/user/{userId}/hearts")
    public ListResponse<HeartDto.Response> getHeartByUserId(@PathVariable Long userId) {
        return responseService.getListResponse(heartService.getHeartsByUserId(userId));
    }
    
}
