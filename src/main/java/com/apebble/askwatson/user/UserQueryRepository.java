package com.apebble.askwatson.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.check.QCheck.check;
import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.heart.QHeart.heart;
import static com.apebble.askwatson.report.QReport.report;
import static com.apebble.askwatson.review.QReview.review;
import static com.apebble.askwatson.user.QUser.user;

/**
 * 회원 화면용 쿼리
 */
@Repository
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public UserQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * [관리자웹] 회원 전체 목록 조회
     */
    public Page<UserQueryDto.Response> findUsersBySearchWord(String searchWord, Pageable pageable) {

        List<UserQueryDto.Response> content = queryFactory
                .select(new QUserQueryDto_Response(
                        user.id,
                        user.userNickname,
                        user.userEmail,
                        user.userBirth.stringValue(),
                        user.userGender,
                        user.marketingAgreeYn,
                        user.createdAt.stringValue(),
                        queryFactory.select(Wildcard.count).from(report).where(report.reportedUser.id.eq(user.id)),
                        queryFactory.select(Wildcard.count).from(review).where(review.user.id.eq(user.id)),
                        queryFactory.select(Wildcard.count).from(check).where(check.user.id.eq(user.id))
                ))
                .from(user)
                .where(searchWordCond(searchWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(heart)
                .where(searchWordCond(searchWord));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression searchWordCond(String searchWord) {
        return (searchWord == null || searchWord.equals(""))
                ? alwaysTrue
                : user.userNickname.contains(searchWord)
                .or(user.userEmail.contains(searchWord));
    }

}
