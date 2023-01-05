package com.apebble.askwatson.report;

import java.util.Optional;

public interface ReportRepositoryCustom {

    Optional<Report> findByIdWithReporterReportedUserReview(Long id);

}
