package com.apebble.askwatson.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {
    List<Report> findByHandledYn(boolean handledYn);

    @Query(value = "select r from Report r where :searchWord is null or (r.reporter.userNickname like %:searchWord% or r.reportedUser.userNickname like %:searchWord% or r.content like %:searchWord% or r.review.content like %:searchWord% or r.review.theme.themeName like %:searchWord% or r.review.theme.cafe.cafeName like %:searchWord%)")
    List<Report> findReportsBySearchWord(String searchWord);

    @Query(value = "select r from Report r where r.handledYn =:handledYn and :searchWord is null or (r.reporter.userNickname like %:searchWord% or r.reportedUser.userNickname like %:searchWord% or r.content like %:searchWord% or r.review.content like %:searchWord% or r.review.theme.themeName like %:searchWord% or r.review.theme.cafe.cafeName like %:searchWord%)")
    List<Report> findReportsByHandledYnAndSearchWord(String searchWord, Boolean handledYn);
}
