package com.apebble.askwatson.like;

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
public class LikesContorller {

    private final LikeService likeService;
    private final ResponseService responseService;


    // 좋아요 등록
    @PostMapping(value="/user/{userId}/themes/{themeId}/likes")
    public SingleResponse<Likes> createLike(@PathVariable Long userId, @PathVariable Long themeId) {
        return responseService.getSingleResponse(likeService.createLike(userId, themeId));
    }


    // 좋아요 해제
    @DeleteMapping(value = "/user/themes/{themeId}/likes/{likeId}")
    public CommonResponse deleteLike(@PathVariable Long themeId, @PathVariable Long likeId){
        likeService.deleteLike(themeId, likeId);
        return responseService.getSuccessResponse();
    }


    // 좋아요 목록
    @GetMapping(value="/user/{userId}/likes")
    public ListResponse<LikesDTO.Response> getMethodName(@PathVariable Long userId) {
        List<Likes> likeList = likeService.getLikeThemeList(userId);
        List<LikesDTO.Response> res = new ArrayList<>(); 
        likeList.forEach(like -> {
            LikesDTO.Response likesDTO = new LikesDTO.Response(like);
            res.add(likesDTO);
        });
        return responseService.getListResponse(res);
    }
}
