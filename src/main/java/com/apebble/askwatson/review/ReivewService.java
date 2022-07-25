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
            .content(params.getContent())
            .theme(theme)
            .build();
        return reviewJpaRepository.save(review);
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
        review.setRating(params.getRating());
        review.setDifficulty(params.getDifficulty());
        review.setTimeTaken(params.getTimeTaken());
        review.setUsedHintNum(params.getUsedHintNum());
        review.setContent(params.getContent());
        return review;
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reviewJpaRepository.delete(review);
    }
}
