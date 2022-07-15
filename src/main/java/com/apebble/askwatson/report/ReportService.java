package com.apebble.askwatson.report;

import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.review.ReviewJpaRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    private final ReportJpaRepository reportJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;

    // 신고 등록
    public Report createReport(Long reporterId, Long reviewId, ReportParams params) {
        User reporter = userJpaRepository.findById(reporterId).orElseThrow(UserNotFoundException::new);
        User reportedUser = userJpaRepository.findById(params.getReportedUserId()).orElseThrow(UserNotFoundException::new);
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        Report report = Report.builder()
                .reporter(reporter)
                .reportedUser(reportedUser)
                .review(review)
                .content(params.getContent())
                .build();

        return reportJpaRepository.save(report);
    }

    // 신고 전체 조회
    public List<Report> getAllReports() {
        return reportJpaRepository.findAll();
    }

    // 처리 여부에 따른 신고 목록 조정
    public List<Report> getReportsByHandledYn(Boolean handledYn) {
        return reportJpaRepository.findByHandledYn(handledYn);
    }

    // 신고 처리 상태 변경
    public void modifyReportHandledYn(Long reportId, Boolean handledYn) {
        Report report = reportJpaRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);
        report.setHandledYn(handledYn);
    }
}
