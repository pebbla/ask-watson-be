package com.apebble.askwatson.review;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.apebble.askwatson.comm.exception.ReviewNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeJpaRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReivewService {

    private final ReviewJpaRepository reviewJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;

    // 리뷰 등록
    public Review createReview(Long userId, Long themeId, ReviewParams params) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        Review review = Review.builder()
            .user(user)
            .rating(params.getRating())
            .difficulty(params.getDifficulty())
            .timeTaken(params.getTimeTaken())
            .usedHintNum(params.getUsedHintNum())
            .deviceRatio(params.getDeviceRatio())
            .activity(params.getActivity())
            .content(params.getContent())
            .theme(theme)
            .build();
        reflectNewReviewInTheme(review, theme);
        return reviewJpaRepository.save(review);
    }

    private void reflectNewReviewInTheme(Review review, Theme theme) {
        // 리뷰 -> 테마 반영해야할 것: rating, deviceRatio, activity

        int reviewCount = theme.getReviewCount();

        double newRating = calculateNewValue(theme.getRating(), review.getRating(), reviewCount);
        double newDeviceRatio = calculateNewValue(theme.getDeviceRatio(), review.getDeviceRatio(), reviewCount);
        double newActivity = calculateNewValue(theme.getActivity(), review.getActivity(), reviewCount);

        theme.updateThemeByReview(newRating, newDeviceRatio, newActivity);
        theme.setReviewCount(reviewCount + 1);
    }

    private double calculateNewValue(double oldValue, double newValue, int reviewCount) {
        return ((oldValue * reviewCount) + newValue) / (reviewCount + 1);
    }

    // 유저별 리뷰 리스트 조회
    public List<Review> getReviewsByUserId(Long userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return reviewJpaRepository.findByUser(user);
    }

    // 테마별 리뷰 리스트 조회
    public List<Review> getReviewsByThemeId(Long themeId) {
        return reviewJpaRepository.findByThemeId(themeId);
    }

    // 리뷰 단건 조회
    public Review getOneReview(Long reviewId) {
        return reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    // 리뷰 수정
    public Review modifyReview(Long reviewId, ReviewParams params) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        Review oldReview = review;
        review.update(params);
        reflectModifiedReviewInTheme(oldReview, review, review.getTheme());
        return review;
    }

    private void reflectModifiedReviewInTheme(Review oldReview, Review newReview, Theme theme) {
        // 전체 평균에서 이전 리뷰 반영 안되게
        reflectDeletedReviewInTheme(oldReview, theme);

        // 새 리뷰 반영
        reflectNewReviewInTheme(newReview, theme);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reflectDeletedReviewInTheme(review, review.getTheme());
        reviewJpaRepository.delete(review);
    }

    private void reflectDeletedReviewInTheme(Review review, Theme theme) {
        int reviewCount = theme.getReviewCount();

        double deletedRating, deletedDeviceRatio, deletedActivity;

        if(reviewCount == 1) {
            deletedRating = deletedDeviceRatio = deletedActivity = 0;
        } else {
            deletedRating = calculateDeletedValue(theme.getRating(), review.getRating(), reviewCount);
            deletedDeviceRatio = calculateDeletedValue(theme.getDeviceRatio(), review.getDeviceRatio(), reviewCount);
            deletedActivity = calculateDeletedValue(theme.getActivity(), review.getActivity(), reviewCount);
        }

        theme.updateThemeByReview(deletedRating, deletedDeviceRatio, deletedActivity);
        theme.setReviewCount(reviewCount - 1);
    }

    private double calculateDeletedValue(double oldValue, double valueToDelete, int reviewCount) {
        return ((oldValue * reviewCount) - valueToDelete) / (reviewCount - 1);
    }
}
