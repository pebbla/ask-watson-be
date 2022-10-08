package com.apebble.askwatson.report;

import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {
    List<Report> findByHandledYn(boolean handledYn);

    List<Report> findByReview(Review review);

    List<Report> findByReporter(User reporter);

    List<Report> findByReportedUser(User reportedUser);

    @Query(value = "select r from Report r where :searchWord is null or (r.reporter.userNickname like %:searchWord% or r.reportedUser.userNickname like %:searchWord% or r.content like %:searchWord% or r.review.content like %:searchWord% or r.review.theme.themeName like %:searchWord% or r.review.theme.cafe.cafeName like %:searchWord%)")
    List<Report> findReportsBySearchWord(@Param("searchWord") String searchWord);

    @Query(value = "select r from Report r where r.handledYn =:handledYn and :searchWord is null or (r.reporter.userNickname like %:searchWord% or r.reportedUser.userNickname like %:searchWord% or r.content like %:searchWord% or r.review.content like %:searchWord% or r.review.theme.themeName like %:searchWord% or r.review.theme.cafe.cafeName like %:searchWord%)")
    List<Report> findReportsByHandledYnAndSearchWord(@Param("searchWord") String searchWord, @Param("handledYn") Boolean handledYn);

    int countByReportedUser(User user);
}
