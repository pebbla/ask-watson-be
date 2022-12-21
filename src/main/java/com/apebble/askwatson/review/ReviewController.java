package com.apebble.askwatson.review;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;

import lombok.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Api(tags = {"리뷰"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final ResponseService responseService;

    // 리뷰 등록
    @PostMapping(value = "/user/{userId}/themes/{themeId}/reviews")
    public SingleResponse<ReviewDto.Response> createReview(@PathVariable Long userId,
                                                           @PathVariable Long themeId,
                                                           @RequestBody ReviewDto.Request params) {
        Long reviewId = reviewService.createReviewByChecks(userId, themeId, params);
        return responseService.getSingleResponse(toDto(reviewService.getOneReview(reviewId)));
    }

    // 유저벌 리뷰 리스트 조회
    @GetMapping(value = "/user/{userId}/reviews")
    public ListResponse<ReviewDto.Response> getReviewsByUserId(@PathVariable Long userId) {
        return responseService.getListResponse(toDtoList(reviewService.getReviewsByUserId(userId)));
    }

    // 테마별 리뷰 리스트 조회
    @GetMapping(value = "/themes/{themeId}/reviews")
    public ListResponse<ReviewDto.Response> getReviewsByThemeId(@PathVariable Long themeId) {
        return responseService.getListResponse(toDtoList(reviewService.getReviewsByThemeId(themeId)));
    }

    // 리뷰 단건 조회
    @GetMapping(value = "/reviews/{reviewId}")
    public SingleResponse<ReviewDto.Response> getOneReview(@PathVariable Long reviewId) {
        return responseService.getSingleResponse(toDto(reviewService.getOneReview(reviewId)));
    }

    // 리뷰 수정
    @PutMapping(value = "/user/reviews/{reviewId}")
    public SingleResponse<ReviewDto.Response> modifyReview(@PathVariable Long reviewId,
                                                           @RequestBody ReviewDto.Request params) {
        reviewService.modifyReview(reviewId, params);
        return responseService.getSingleResponse(toDto(reviewService.getOneReview(reviewId)));
    }

    // 리뷰 삭제
    @DeleteMapping(value = "/reviews/{reviewId}")
    public CommonResponse deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return responseService.getSuccessResponse();
    }

    //==DTO 변환 메서드==//
    private List<ReviewDto.Response> toDtoList(List<Review> reviewList){
        return reviewList.stream().map(ReviewDto.Response::new).collect(toList());
    }

    private ReviewDto.Response toDto(Review review){
        return new ReviewDto.Response(review);
    }

}
