package com.apebble.askwatson.review;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.escapecomplete.EscapeCompleteService;
import com.apebble.askwatson.report.Report;
import com.apebble.askwatson.report.ReportJpaRepository;
import org.springframework.stereotype.Service;

import com.apebble.askwatson.comm.exception.EscapeCompleteNotFoundException;
import com.apebble.askwatson.comm.exception.ReviewNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.comm.util.DateConverter;
import com.apebble.askwatson.escapecomplete.EscapeComplete;
import com.apebble.askwatson.escapecomplete.EscapeCompleteJpaRepository;
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
    private final EscapeCompleteJpaRepository escapeCompleteJpaRepository;
    private final EscapeCompleteService escapeCompleteService;
    private final ReportJpaRepository reportJpaRepository;

    // 리뷰 등록(탈출완료 여부 확인)
    public ReviewDto.Response createReviewByCheckingEscapeComplete(Long userId, Long themeId, ReviewParams params) {
        if(!doesEscapeCompleteExists(userId, themeId))
            escapeCompleteService.createEscapeComplete(userId, themeId);

        return convertToReviewDto(createReview(userId, themeId, params));
    }

    private boolean doesEscapeCompleteExists(Long userId, Long themeId) {
        Optional<EscapeComplete> escapeComplete = escapeCompleteJpaRepository.findByUserIdAndThemeId(userId, themeId);
        return escapeComplete.isPresent();
    }

    private Review createReview(Long userId, Long themeId, ReviewParams params) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        EscapeComplete escapeComplete = escapeCompleteJpaRepository.findByUserIdAndThemeId(userId, themeId)
                .orElseThrow(EscapeCompleteNotFoundException::new);
        
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

        reflectNewReviewInCafeAndTheme(params, theme);
        LocalDate parseLocalDate = DateConverter.strToLocalDate(params.getEscapeCompleteDate());
        escapeComplete.update(parseLocalDate);

        return reviewJpaRepository.save(review);
    }

    private void reflectNewReviewInCafeAndTheme(ReviewParams review, Theme theme) {
        reflectNewReviewInCafe(review, theme.getCafe());
        reflectNewReviewInTheme(review, theme);
    }

    private void reflectNewReviewInCafe(ReviewParams review, Cafe cafe) {
        int reviewCount = cafe.getReviewCount();
        double newRating = calculateNewValue(cafe.getRating(), review.getRating(), reviewCount);

        cafe.updateCafeByReview(newRating);
        cafe.incReviewCount();
    }

    private void reflectNewReviewInTheme(ReviewParams review, Theme theme) {
        int reviewCount = theme.getReviewCount();
        double newRating = calculateNewValue(theme.getRating(), review.getRating(), reviewCount);
        double newDeviceRatio = calculateNewValue(theme.getDeviceRatio(), review.getDeviceRatio(), reviewCount);
        double newActivity = calculateNewValue(theme.getActivity(), review.getActivity(), reviewCount);

        theme.updateThemeByReview(newRating, newDeviceRatio, newActivity);
        theme.incReviewCount();
    }

    private double calculateNewValue(double oldValue, double newValue, int reviewCount) {
        return ((oldValue * reviewCount) + newValue) / (reviewCount + 1);
    }

    // 유저별 리뷰 리스트 조회
    public List<ReviewDto.Response> getReviewsByUserId(Long userId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return convertToReviewDtoList(reviewJpaRepository.findByUser(user));
    }

    // 테마별 리뷰 리스트 조회
    public List<ReviewDto.Response> getReviewsByThemeId(Long themeId) {
        return convertToReviewDtoList(reviewJpaRepository.findByThemeId(themeId));
    }

    // 리뷰 단건 조회
    public ReviewDto.Response getOneReview(Long reviewId) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        return convertToReviewDto(review);
    }

    // 리뷰 수정
    public ReviewDto.Response modifyReview(Long reviewId, ReviewParams params) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reflectModifiedReviewInCafeAndTheme(review, params, review.getTheme());
        review.update(params);
        return convertToReviewDto(review);
    }

    private void reflectModifiedReviewInCafeAndTheme(Review oldReview, ReviewParams newReview, Theme theme) {
        reflectReviewDeletionInCafeAndTheme(oldReview, theme);
        reflectNewReviewInCafeAndTheme(newReview, theme);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        reflectReviewDeletionInCafeAndTheme(review, review.getTheme());
        setReportsReviewNull(review);
        reviewJpaRepository.delete(review);
    }

    private void reflectReviewDeletionInCafeAndTheme(Review review, Theme theme) {
        reflectReviewDeletionInCafe(review, theme.getCafe());
        reflectReviewDeletionInTheme(review, theme);
    }

    private void reflectReviewDeletionInCafe(Review review, Cafe cafe) {
        int reviewCount = cafe.getReviewCount();
        double deletedRating=0;

        if(reviewCount > 1) {
            deletedRating = calculateDeletedValue(cafe.getRating(), review.getRating(),reviewCount);
        }

        cafe.updateCafeByReview(deletedRating);
        cafe.decReviewCount();
    }

    private void reflectReviewDeletionInTheme(Review review, Theme theme) {
        int reviewCount = theme.getReviewCount();
        double deletedRating=0, deletedDeviceRatio=0, deletedActivity=0;

        if(reviewCount > 1) {
            deletedRating = calculateDeletedValue(theme.getRating(), review.getRating(), reviewCount);
            deletedDeviceRatio = calculateDeletedValue(theme.getDeviceRatio(), review.getDeviceRatio(), reviewCount);
            deletedActivity = calculateDeletedValue(theme.getActivity(), review.getActivity(), reviewCount);
        }

        theme.updateThemeByReview(deletedRating, deletedDeviceRatio, deletedActivity);
        theme.decReviewCount();
    }

    private double calculateDeletedValue(double oldValue, double valueToDelete, int reviewCount) {
        return ((oldValue * reviewCount) - valueToDelete) / (reviewCount - 1);
    }

    private void setReportsReviewNull(Review review) {
        List<Report> reports = reportJpaRepository.findByReview(review);
        reports.forEach(report -> report.setReview(null));
    }

    private List<ReviewDto.Response> convertToReviewDtoList(List<Review> reviewList){
        return reviewList.stream().map(ReviewDto.Response::new).collect(toList());
    }

    private ReviewDto.Response convertToReviewDto(Review review){
        return new ReviewDto.Response(review);
    }
}
