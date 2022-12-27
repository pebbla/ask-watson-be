package com.apebble.askwatson.report;

import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom {

    List<Report> findByReview(Review review);

    List<Report> findByReporter(User reporter);

    List<Report> findByReportedUser(User reportedUser);

    int countByReportedUser(User user);

}
