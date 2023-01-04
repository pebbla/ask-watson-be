package com.apebble.askwatson.heart;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"좋아요"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class HeartController {

    private final HeartService heartService;
    private final HeartQueryRepository heartQueryRepository;
    private final ResponseService responseService;

    // 좋아요 등록
    @PostMapping(value="/user/{userId}/themes/{themeId}/hearts")
    public SingleResponse<Long> createHeart(@PathVariable Long userId, @PathVariable Long themeId) {
        Long heartId = heartService.createHeart(userId, themeId);
        return responseService.getSingleResponse(heartId);
    }

    // 좋아요 해제
    @DeleteMapping(value = "/user/hearts/{heartId}")
    public CommonResponse deleteHeart(@PathVariable Long heartId){
        heartService.deleteHeart(heartId);
        return responseService.getSuccessResponse();
    }

    // 좋아요 목록
    @GetMapping(value="/user/{userId}/hearts")
    public PageResponse<HeartQueryDto.Response> getHeartByUserId(@PathVariable Long userId, Pageable pageable) {
        return responseService.getPageResponse(heartQueryRepository.getHeartsByUserId(userId, pageable));
    }
    
}
