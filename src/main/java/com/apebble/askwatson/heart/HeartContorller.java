package com.apebble.askwatson.heart;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class HeartContorller {

    private final HeartService heartService;
    private final ResponseService responseService;


    // 좋아요 등록
    @PostMapping(value="/user/{userId}/themes/{themeId}/hearts")
    public SingleResponse<Heart> createHeart(@PathVariable Long userId, @PathVariable Long themeId) {
        return responseService.getSingleResponse(heartService.createHeart(userId, themeId));
    }


    // 좋아요 해제
    @DeleteMapping(value = "/user/themes/{themeId}/hearts/{heartId}")
    public CommonResponse deleteHeart(@PathVariable Long themeId, @PathVariable Long heartId){
        heartService.deleteHeart(themeId, heartId);
        return responseService.getSuccessResponse();
    }


    // 좋아요 목록
    @GetMapping(value="/user/{userId}/hearts")
    public ListResponse<HeartDTO.Response> getHeartByUserId(@PathVariable Long userId) {
        List<Heart> heartList = heartService.getHeartsByUserId(userId);
        List<HeartDTO.Response> res = new ArrayList<>(); 
        heartList.forEach(heart -> {
            HeartDTO.Response heartDTO = new HeartDTO.Response(heart);
            res.add(heartDTO);
        });
        return responseService.getListResponse(res);
    }
}
