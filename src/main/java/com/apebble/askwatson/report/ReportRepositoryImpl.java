package com.apebble.askwatson.report;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.report.QReport.report;
import static com.apebble.askwatson.review.QReview.review;
import static com.apebble.askwatson.user.QUser.user;

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReportRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Report> findReportsBySearchWord(String searchWord) {
        return queryFactory
                .selectFrom(report)
                .leftJoin(report.reporter, user).fetchJoin()
                .leftJoin(report.reportedUser, user).fetchJoin()
                .where(searchWordCond(searchWord))
                .fetch();
    }

    private BooleanExpression searchWordCond(String searchWord) {
        return (searchWord == null || searchWord.equals(""))
                ? alwaysTrue
                : reporterContains(searchWord)
                .or(reportedUserContains(searchWord))
                .or(report.content.contains(searchWord))
                .or(report.reviewContent.contains(searchWord));
    }

    private BooleanExpression reporterContains(String searchWord) {
        return (report.reporter == null)
                ? alwaysTrue
                : report.reporter.userNickname.contains(searchWord);
    }

    private BooleanExpression reportedUserContains(String searchWord) {
        return (report.reportedUser == null)
                ? alwaysTrue
                : report.reporter.userNickname.contains(searchWord);
    }

    @Override
    public List<Report> findReportsByHandledYnAndSearchWord(String searchWord, Boolean handledYn) {
        return queryFactory
                .selectFrom(report)
                .leftJoin(report.reporter, user).fetchJoin()
                .leftJoin(report.reportedUser, user).fetchJoin()
                .where(searchWordCond(searchWord),
                        report.handledYn.eq(handledYn))
                .fetch();
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
