package com.apebble.askwatson.report;

import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.review.ReviewRepository;
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

    private final ReportRepository reportRepository;
    private final UserJpaRepository userJpaRepository;
    private final ReviewRepository reviewRepository;


    /**
     * 신고 등록
     */
    public Long createReport(Long reporterId, Long reviewId, ReportDto.Request params) {
        User reporter = userJpaRepository.findById(reporterId).orElseThrow(UserNotFoundException::new);
        Review review = reviewRepository.findByIdWithUser(reviewId).orElseThrow(ReviewNotFoundException::new);
        return reportRepository.save(Report.create(reporter, review, params)).getId();
    }


    /**
     * 신고 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Report> getAllReports(String searchWord) {
        return reportRepository.findReportsBySearchWord(searchWord);
    }


    /**
     * 신고 단건 조회
     */
    @Transactional(readOnly = true)
    public Report getOneReport(Long reportId) {
        return reportRepository.findByIdWithReporterReportedUserReview(reportId).orElseThrow(ReportNotFoundException::new);
    }


    /**
     * 처리 여부에 따른 신고 목록 조정
     */
    public List<Report> getReportsByHandledYn(String searchWord, Boolean handledYn) {
        return reportRepository.findReportsByHandledYnAndSearchWord(searchWord, handledYn);
    }


    /**
     * 신고 처리 상태 변경
     */
    public void modifyReportHandledYn(Long reportId, Boolean handledYn) {
        Report report = reportRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);
        report.updateHandledYn(handledYn);
    }

}
