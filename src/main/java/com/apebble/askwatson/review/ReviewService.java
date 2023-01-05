package com.apebble.askwatson.review;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.check.CheckService;
import com.apebble.askwatson.report.Report;
import com.apebble.askwatson.report.ReportRepository;
import org.springframework.stereotype.Service;

import com.apebble.askwatson.comm.exception.CheckNotFoundException;
import com.apebble.askwatson.comm.exception.ReviewNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.comm.util.DateConverter;
import com.apebble.askwatson.check.Check;
import com.apebble.askwatson.check.CheckRepository;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserJpaRepository userJpaRepository;
    private final ThemeRepository themeRepository;
    private final CheckRepository checkRepository;
    private final CheckService checkService;
    private final ReportRepository reportRepository;


    /**
     * 리뷰 등록
     */
    public Long createReviewByChecks(Long userId, Long themeId, ReviewDto.Request params) {
        Check check = findOrCreateCheck(userId, themeId);
        return createReview(userId, themeId, check, params);
    }

    private Check findOrCreateCheck(Long userId, Long themeId) {
        Optional<Check> check = checkRepository.findByUserIdAndThemeId(userId, themeId);
        if(check.isPresent()) {
            return check.orElseThrow(CheckNotFoundException::new);
        }
        else {
            Long id = checkService.createCheck(userId, themeId);
            return checkRepository.findById(id).orElseThrow(CheckNotFoundException::new);
        }
    }

    private Long createReview(Long userId, Long themeId, Check check, ReviewDto.Request params) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        addReviewToCafeAndTheme(params, theme);
        updateCheckDt(check, params.getCheckDate());
        return reviewRepository.save(Review.create(user, theme, check, params)).getId();
    }

    private void addReviewToCafeAndTheme(ReviewDto.Request review, Theme theme) {
        addReviewToCafe(review, theme.getCafe());
        addReviewToTheme(review, theme);
    }

    private void addReviewToCafe(ReviewDto.Request review, Cafe cafe) {
        int reviewCount = cafe.getReviewCount();
        double newRating = calculateNewValue(cafe.getRating(), review.getRating(), reviewCount);

        cafe.updateRating(newRating);
        cafe.incReviewCount();
    }

    private void addReviewToTheme(ReviewDto.Request review, Theme theme) {
        int reviewCount = theme.getReviewCount();
        double newRating = calculateNewValue(theme.getRating(), review.getRating(), reviewCount);
        double newDeviceRatio = calculateNewValue(theme.getDeviceRatio(), review.getDeviceRatio(), reviewCount);
        double newActivity = calculateNewValue(theme.getActivity(), review.getActivity(), reviewCount);

        theme.updateByReview(newRating, newDeviceRatio, newActivity);
        theme.incReviewCount();
    }

    private double calculateNewValue(double oldValue, double newValue, int reviewCount) {
        return ((oldValue * reviewCount) + newValue) / (reviewCount + 1);
    }

    private void updateCheckDt(Check check, String newCheckDt) {
        LocalDate parseLocalDate = DateConverter.strToLocalDate(newCheckDt);
        check.update(parseLocalDate);
    }


    /**
     * 리뷰 단건 조회
     */
    @Transactional(readOnly = true)
    public Review getOneReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
    }


    /**
     * 리뷰 수정
     */
    public void modifyReview(Long reviewId, ReviewDto.Request params) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        updateCafeAndTheme(review, params, review.getTheme());
        review.update(params);
    }

    private void updateCafeAndTheme(Review oldReview, ReviewDto.Request newReview, Theme theme) {
        deleteReviewInCafeAndTheme(oldReview);
        addReviewToCafeAndTheme(newReview, theme);
    }


    /**
     * 리뷰 삭제
     */
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        deleteReviewInCafeAndTheme(review);
        deleteReviewInReport(review);
        reviewRepository.delete(review);
    }

    private void deleteReviewInCafeAndTheme(Review review) {
        deleteReviewInCafe(review);
        deleteReviewInTheme(review);
    }

    private void deleteReviewInCafe(Review review) {
        Cafe cafe = review.getTheme().getCafe();
        int reviewCount = cafe.getReviewCount();

        double deletedRating=0;
        if(reviewCount > 1) {
            deletedRating = calculateDeletedValue(cafe.getRating(), review.getRating(),reviewCount);
        }

        cafe.updateRating(deletedRating);
        cafe.decReviewCount();
    }

    private void deleteReviewInTheme(Review review) {
        Theme theme = review.getTheme();
        int reviewCount = theme.getReviewCount();
        double deletedRating=0, deletedDeviceRatio=0, deletedActivity=0;

        if(reviewCount > 1) {
            deletedRating = calculateDeletedValue(theme.getRating(), review.getRating(), reviewCount);
            deletedDeviceRatio = calculateDeletedValue(theme.getDeviceRatio(), review.getDeviceRatio(), reviewCount);
            deletedActivity = calculateDeletedValue(theme.getActivity(), review.getActivity(), reviewCount);
        }

        theme.updateByReview(deletedRating, deletedDeviceRatio, deletedActivity);
        theme.decReviewCount();
    }

    private double calculateDeletedValue(double oldValue, double valueToDelete, int reviewCount) {
        return ((oldValue * reviewCount) - valueToDelete) / (reviewCount - 1);
    }

    private void deleteReviewInReport(Review review) {
        List<Report> reports = reportRepository.findByReview(review);
        reports.forEach(Report::deleteReview);
    }

}
