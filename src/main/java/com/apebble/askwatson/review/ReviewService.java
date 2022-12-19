package com.apebble.askwatson.review;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.check.CheckService;
import com.apebble.askwatson.report.Report;
import com.apebble.askwatson.report.ReportJpaRepository;
import org.springframework.stereotype.Service;

import com.apebble.askwatson.comm.exception.CheckNotFoundException;
import com.apebble.askwatson.comm.exception.ReviewNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.comm.util.DateConverter;
import com.apebble.askwatson.check.Check;
import com.apebble.askwatson.check.CheckJpaRepository;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeJpaRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewJpaRepository reviewJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;
    private final CheckJpaRepository checkJpaRepository;
    private final CheckService checkService;
    private final ReportJpaRepository reportJpaRepository;


    /**
     * 리뷰 등록
     */
    public Long createReviewByChecks(Long userId, Long themeId, ReviewParams params) {
        Check check = findOrCreateCheck(userId, themeId);
        return createReview(userId, themeId, check, params);
    }

    private Check findOrCreateCheck(Long userId, Long themeId) {
        Optional<Check> check = checkJpaRepository.findByUserIdAndThemeId(userId, themeId);
        if(check.isPresent()) {
            return check.orElseThrow(CheckNotFoundException::new);
        }
        else {
            Long id = checkService.createCheck(userId, themeId);
            return checkJpaRepository.findById(id).orElseThrow(CheckNotFoundException::new);
        }
    }

    private Long createReview(Long userId, Long themeId, Check check, ReviewParams params) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        addReviewToCafeAndTheme(params, theme);
        updateCheckDt(check, params.getCheckDate());
        return reviewJpaRepository.save(Review.create(user, theme, check, params)).getId();
    }

    private void addReviewToCafeAndTheme(ReviewParams review, Theme theme) {
        addReviewToCafe(review, theme.getCafe());
        addReviewToTheme(review, theme);
    }

    private void addReviewToCafe(ReviewParams review, Cafe cafe) {
        int reviewCount = cafe.getReviewCount();
        double newRating = calculateNewValue(cafe.getRating(), review.getRating(), reviewCount);

        cafe.updateRating(newRating);
        cafe.incReviewCount();
    }

    private void addReviewToTheme(ReviewParams review, Theme theme) {
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
     * 사용자별 리뷰 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ReviewDto.Response> getReviewsByUserId(Long userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return convertToReviewDtoList(reviewJpaRepository.findByUser(user));
    }

    /**
     * 테마별 리뷰 리스트 조회
     */
    @Transactional(readOnly = true)
    public List<ReviewDto.Response> getReviewsByThemeId(Long themeId) {
        return convertToReviewDtoList(reviewJpaRepository.findByThemeId(themeId));
    }


    /**
     * 리뷰 단건 조회
     */
    @Transactional(readOnly = true)
    public ReviewDto.Response getOneReview(Long reviewId) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        return convertToReviewDto(review);
    }


    /**
     * 리뷰 수정
     */
    public void modifyReview(Long reviewId, ReviewParams params) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        updateCafeAndTheme(review, params, review.getTheme());
        review.update(params);
    }

    private void updateCafeAndTheme(Review oldReview, ReviewParams newReview, Theme theme) {
        deleteReviewInCafeAndTheme(oldReview);
        addReviewToCafeAndTheme(newReview, theme);
    }


    /**
     * 리뷰 삭제
     */
    public void deleteReview(Long reviewId) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        deleteReviewInCafeAndTheme(review);
        deleteReviewInReport(review);
        reviewJpaRepository.delete(review);
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
        List<Report> reports = reportJpaRepository.findByReview(review);
        reports.forEach(Report::deleteReview);
    }


    //==DTO 변환 메서드==//
    private List<ReviewDto.Response> convertToReviewDtoList(List<Review> reviewList){
        return reviewList.stream().map(ReviewDto.Response::new).collect(toList());
    }

    private ReviewDto.Response convertToReviewDto(Review review){
        return new ReviewDto.Response(review);
    }

}
