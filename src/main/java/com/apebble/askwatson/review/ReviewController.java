package com.apebble.askwatson.review;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;

import lombok.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Api(tags = {"리뷰"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final ResponseService responseService;

    // 리뷰 등록
    @PostMapping(value = "/user/{userId}/themes/{themeId}/reviews")
    public SingleResponse<Review> createReview(@PathVariable Long userId, @PathVariable Long themeId,
            @ModelAttribute ReviewParams params) {
        return responseService.getSingleResponse(reviewService.createReview(userId, themeId, params));
    }

    // 유저벌 리뷰 리스트 조회
    @GetMapping(value = "/user/{userId}/reviews")
    public ListResponse<Review> getReviewsByUserId(@PathVariable Long userId) {
        return responseService.getListResponse(reviewService.getReviewsByUserId(userId));
    }

    // 테마별 리뷰 리스트 조회
    @GetMapping(value = "/reviews/theme/{themeId}")
    public ListResponse<Review> getReviewsByThemeId(@PathVariable Long themeId) {
        return responseService.getListResponse(reviewService.getReviewsByThemeId(themeId));
    }

    // 리뷰 단건 조회
    @GetMapping(value = "/reviews/{reviewId}")
    public SingleResponse<Review> getOneReview(@PathVariable Long reviewId) {
        return responseService.getSingleResponse(reviewService.getOneReview(reviewId));
    }

    // 리뷰 수정
    @PutMapping(value = "/user/reviews/{reviewId}")
    public SingleResponse<Review> modifyReview(@PathVariable Long reviewId, @ModelAttribute ReviewParams params) {
        return responseService.getSingleResponse(reviewService.modifyReview(reviewId, params));
    }

    // 리뷰 삭제
    @DeleteMapping(value = "/reviews/{reviewId}")
    public CommonResponse deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return responseService.getSuccessResponse();
    }
}
