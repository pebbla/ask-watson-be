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

import static java.util.stream.Collectors.toList;

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
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        Report report = Report.builder()
                .reporter(reporter)
                .reportedUser(review.getUser())
                .review(review)
                .content(params.getContent())
                .reviewContent(review.getContent())
                .build();

        return reportJpaRepository.save(report);
    }

    // 신고 전체 조회
    public List<ReportDto.Response> getAllReports(String searchWord) {
        List<Report> reportList = (searchWord == null)
                ? reportJpaRepository.findAll()
                : reportJpaRepository.findReportsBySearchWord(searchWord);

        return convertToReportDtoList(reportList);
    }

    // 처리 여부에 따른 신고 목록 조정
    public List<ReportDto.Response> getReportsByHandledYn(String searchWord, Boolean handledYn) {
        List<Report> reportList = (searchWord == null)
                ? reportJpaRepository.findByHandledYn(handledYn)
                : reportJpaRepository.findReportsByHandledYnAndSearchWord(searchWord, handledYn);

        return convertToReportDtoList(reportList);
    }

    private List<ReportDto.Response> convertToReportDtoList(List<Report> reportList){
        return reportList.stream().map(ReportDto.Response::new).collect(toList());
    }

    // 신고 처리 상태 변경
    public void modifyReportHandledYn(Long reportId, Boolean handledYn) {
        Report report = reportJpaRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);
        report.setHandledYn(handledYn);
    }
}
