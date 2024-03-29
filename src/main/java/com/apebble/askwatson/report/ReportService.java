package com.apebble.askwatson.report;

import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.review.ReviewRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    /**
     * 신고 등록
     */
    public Long createReport(Long reporterId, Long reviewId, ReportDto.Request params) {
        User reporter = userRepository.findById(reporterId).orElseThrow(UserNotFoundException::new);
        Review review = reviewRepository.findByIdWithUser(reviewId).orElseThrow(ReviewNotFoundException::new);
        return reportRepository.save(Report.create(reporter, review, params)).getId();
    }


    /**
     * 신고 단건 조회
     */
    @Transactional(readOnly = true)
    public Report getOneReport(Long reportId) {
        return reportRepository.findByIdWithReporterReportedUserReview(reportId).orElseThrow(ReportNotFoundException::new);
    }


    /**
     * 신고 처리 상태 변경
     */
    public void modifyReportHandledYn(Long reportId, Boolean handledYn) {
        Report report = reportRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);
        report.updateHandledYn(handledYn);
    }

}
