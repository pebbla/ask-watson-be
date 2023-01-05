package com.apebble.askwatson.report;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.apebble.askwatson.review.QReview.review;
import static com.apebble.askwatson.user.QUser.user;

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReportRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Report> findByIdWithReporterReportedUserReview(Long id) {
        Report findReport = queryFactory
                .selectFrom(QReport.report)
                .join(QReport.report.reporter, user).fetchJoin()
                .join(QReport.report.reportedUser, user).fetchJoin()
                .join(QReport.report.review, review).fetchJoin()
                .where(QReport.report.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(findReport);
    }

}
