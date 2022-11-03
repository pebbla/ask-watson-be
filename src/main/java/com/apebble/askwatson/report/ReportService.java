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
    public ReportDto.Response createReport(Long reporterId, Long reviewId, ReportParams params) {
        User reporter = userJpaRepository.findById(reporterId).orElseThrow(UserNotFoundException::new);
        Review review = reviewJpaRepository.findByIdWithUser(reviewId).orElseThrow(ReviewNotFoundException::new);

        Report report = Report.builder()
                .reporter(reporter)
                .reportedUser(review.getUser())
                .content(params.getContent())
                .review(review)
                .reviewContent(review.getContent())
                .build();

        return convertToReportDto(reportJpaRepository.save(report));
    }

    // 신고 전체 조회
    public List<ReportDto.Response> getAllReports(String searchWord) {
        List<Report> reportList = (searchWord == null)
                ? reportJpaRepository.findAllReports()
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

    // 신고 처리 상태 변경
    public void modifyReportHandledYn(Long reportId, Boolean handledYn) {
        Report report = reportJpaRepository.findById(reportId).orElseThrow(ReportNotFoundException::new);
        report.updateHandledYn(handledYn);
    }

    //== DTO 변환 메서드==//
    private List<ReportDto.Response> convertToReportDtoList(List<Report> reportList){
        return reportList.stream().map(ReportDto.Response::new).collect(toList());
    }

    private ReportDto.Response convertToReportDto(Report report){
        return new ReportDto.Response(report);
    }

}
