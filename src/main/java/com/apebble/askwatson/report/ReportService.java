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


    /**
     * 신고 등록
     */
    public Long createReport(Long reporterId, Long reviewId, ReportDto.Request params) {
        User reporter = userJpaRepository.findById(reporterId).orElseThrow(UserNotFoundException::new);
        Review review = reviewJpaRepository.findByIdWithUser(reviewId).orElseThrow(ReviewNotFoundException::new);
        return reportJpaRepository.save(Report.create(reporter, review, params)).getId();
    }


    /**
     * 신고 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Report> getAllReports(String searchWord) {
        List<Report> reportList = (searchWord == null)
                ? reportJpaRepository.findAllReports()
                : reportJpaRepository.findReportsBySearchWord(searchWord);

        return reportList;
    }


    /**
     * 신고 단건 조회
     */
    @Transactional(readOnly = true)
    public Report getOneReport(Long reportId) {
        return reportJpaRepository.findByIdWithReporterReportedUserReview(reportId).orElseThrow(ReportNotFoundException::new);
    }


    /**
     * 처리 여부에 따른 신고 목록 조정
     */
    public List<Report> getReportsByHandledYn(String searchWord, Boolean handledYn) {
        List<Report> reportList = (searchWord == null)
                ? reportJpaRepository.findByHandledYn(handledYn)
                : reportJpaRepository.findReportsByHandledYnAndSearchWord(searchWord, handledYn);

        return reportList;
    }


    /**
     * 신고 처리 상태 변경
     */
    public void modifyReportHandledYn(Long reportId, Boolean handledYn) {
        Report report = reportJpaRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);
        report.updateHandledYn(handledYn);
    }

}
