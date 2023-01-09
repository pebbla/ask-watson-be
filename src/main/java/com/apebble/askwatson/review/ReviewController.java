package com.apebble.askwatson.review;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import lombok.*;

@Api(tags = {"리뷰"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewQueryRepository reviewQueryRepository;
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
    public PageResponse<ReviewQueryDto.Response> getReviewsByUserId(@PathVariable Long userId, Pageable pageable) {
        return responseService.getPageResponse(reviewQueryRepository.getReviewsByUserId(userId, pageable));
    }

    // 테마별 리뷰 리스트 조회
    @GetMapping(value = "/themes/{themeId}/reviews")
    public PageResponse<ReviewQueryDto.Response> getReviewsByThemeId(@PathVariable Long themeId, Pageable pageable) {
        return responseService.getPageResponse(reviewQueryRepository.getReviewsByThemeId(themeId, pageable));
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
    private ReviewDto.Response toDto(Review review){
        return new ReviewDto.Response(review);
    }

}
