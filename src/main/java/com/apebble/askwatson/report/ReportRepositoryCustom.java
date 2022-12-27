package com.apebble.askwatson.report;

import java.util.List;
import java.util.Optional;

public interface ReportRepositoryCustom {

    List<Report> findReportsBySearchWord(String searchWord);

    List<Report> findReportsByHandledYnAndSearchWord(String searchWord, Boolean handledYn);

    Optional<Report> findByIdWithReporterReportedUserReview(Long id);

}
