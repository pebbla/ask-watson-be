package com.apebble.askwatson.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {
    List<Report> findByHandledYn(boolean handledYn);
}
