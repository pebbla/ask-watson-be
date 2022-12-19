package com.apebble.askwatson.review;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;

import lombok.*;

@Api(tags = {"리뷰"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final ResponseService responseService;

    // 리뷰 등록
    @PostMapping(value = "/user/{userId}/themes/{themeId}/reviews")
    public SingleResponse<ReviewDto.Response> createReview(
            @PathVariable Long userId, @PathVariable Long themeId, @RequestBody ReviewParams params) {
        Long reviewId = reviewService.createReviewByChecks(userId, themeId, params);
        return responseService.getSingleResponse(reviewService.getOneReview(reviewId));
    }

    // 유저벌 리뷰 리스트 조회
    @GetMapping(value = "/user/{userId}/reviews")
    public ListResponse<ReviewDto.Response> getReviewsByUserId(@PathVariable Long userId) {
        return responseService.getListResponse(reviewService.getReviewsByUserId(userId));
    }

    // 테마별 리뷰 리스트 조회
    @GetMapping(value = "/themes/{themeId}/reviews")
    public ListResponse<ReviewDto.Response> getReviewsByThemeId(@PathVariable Long themeId) {
        return responseService.getListResponse(reviewService.getReviewsByThemeId(themeId));
    }

    // 리뷰 단건 조회
    @GetMapping(value = "/reviews/{reviewId}")
    public SingleResponse<ReviewDto.Response> getOneReview(@PathVariable Long reviewId) {
        return responseService.getSingleResponse(reviewService.getOneReview(reviewId));
    }

    // 리뷰 수정
    @PutMapping(value = "/user/reviews/{reviewId}")
    public SingleResponse<ReviewDto.Response> modifyReview(
            @PathVariable Long reviewId, @RequestBody ReviewParams params) {
        reviewService.modifyReview(reviewId, params);
        return responseService.getSingleResponse(reviewService.getOneReview(reviewId));
    }

    // 리뷰 삭제
    @DeleteMapping(value = "/reviews/{reviewId}")
    public CommonResponse deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return responseService.getSuccessResponse();
    }
}
