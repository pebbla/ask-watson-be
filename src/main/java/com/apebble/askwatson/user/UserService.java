package com.apebble.askwatson.user;

import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.check.Check;
import com.apebble.askwatson.check.CheckRepository;
import com.apebble.askwatson.check.CheckService;
import com.apebble.askwatson.report.Report;
import com.apebble.askwatson.report.ReportRepository;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.review.ReviewRepository;
import com.apebble.askwatson.suggestion.Suggestion;
import com.apebble.askwatson.suggestion.SuggestionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ReviewRepository reviewRepository;
    private final SuggestionRepository suggestionRepository;
    private final CheckRepository checkRepository;
    private final CheckService checkService;


    /**
     * 회원정보 수정
     */
    public void modifyUser(Long userId, UserDto.Request params) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(params);
    }


    /**
     * 회원 삭제
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        handleUserAssociations(user);
        userRepository.delete(user);
    }

    private void handleUserAssociations(User user) {
        deleteChecksHandlingReviews(user);
        setReviewsUserNull(user);
        setReportsReporterNull(user);
        setReportsReportedUserNull(user);
        setSuggestionsUserNull(user);
    }

    private void setReviewsUserNull(User user) {
        List<Review> reviews = reviewRepository.findByUser(user);
        reviews.forEach(Review::deleteUser);
    }

    private void deleteChecksHandlingReviews(User user) {
        List<Check> checks = checkRepository.findByUserId(user.getId());
        setReviewsCheckNull(user);
        checks.forEach(checkService::deleteCheck);
    }

    private void setReviewsCheckNull(User user) {
        List<Review> reviews = reviewRepository.findByUser(user);
        reviews.forEach(Review::deleteCheck);
    }

    private void setReportsReporterNull(User user) {
        List<Report> reports = reportRepository.findByReporter(user);
        reports.forEach(Report::deleteReporter);
    }

    private void setReportsReportedUserNull(User user) {
        List<Report> reports = reportRepository.findByReportedUser(user);
        reports.forEach(Report::deleteReportedUser);
    }

    private void setSuggestionsUserNull(User user) {
        List<Suggestion> suggestions = suggestionRepository.findByUser(user);
        suggestions.forEach(Suggestion::deleteUser);
    }

}
