package com.apebble.askwatson.report;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.cafe.QCafe.cafe;
import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.report.QReport.report;
import static com.apebble.askwatson.review.QReview.review;
import static com.apebble.askwatson.theme.QTheme.theme;
import static com.apebble.askwatson.user.QUser.user;


/**
 * 신고 화면용 쿼리
 */
@Repository
public class ReportQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReportQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * [관리자웹] 신고 목록 전체 조회
     */
    public List<ReportQueryDto.Response> getReports(String searchWord, Boolean handledYn) {
        return queryFactory
                .select(new QReportQueryDto_Response(
                        report.id,
                        report.content,
                        report.reviewContent,
                        review.id,
                        cafe.cafeName,
                        theme.themeName,
                        report.createdAt.stringValue(),
                        report.handledYn,
                        report.reporter.id,
                        report.reporter.userNickname,
                        report.reportedUser.id,
                        report.reportedUser.userNickname
                ))
                .from(report)
                .leftJoin(report.review, review)
                .leftJoin(review.theme, theme)
                .leftJoin(theme.cafe, cafe)
                .leftJoin(report.reporter, user)
                .leftJoin(report.reportedUser, user)
                .where(searchWordCond(searchWord),
                        handledYnCond(handledYn))
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
                : report.reportedUser.userNickname.contains(searchWord);
    }

    private BooleanExpression handledYnCond(Boolean handledYn) {
        return (handledYn == null)
                ? alwaysTrue
                : report.handledYn.eq(handledYn);
    }

}
