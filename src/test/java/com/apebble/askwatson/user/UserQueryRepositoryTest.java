package com.apebble.askwatson.user;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.apebble.askwatson.check.QCheck.*;
import static com.apebble.askwatson.report.QReport.report;
import static com.apebble.askwatson.review.QReview.review;
import static com.apebble.askwatson.user.QUser.user;


@SpringBootTest
@Transactional
class UserQueryRepositoryTest {
    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Test
    public void findUsersBySearchWord() {
        queryFactory = new JPAQueryFactory(em);

        JPAQuery<Long> reportedCount = queryFactory.select(Wildcard.count).from(report).where(report.reportedUser.id.eq(user.id));
        JPAQuery<Long> reviewCount = queryFactory.select(Wildcard.count).from(review).where(review.user.id.eq(user.id));
        JPAQuery<Long> checkCount = queryFactory.select(Wildcard.count).from(check).where(check.user.id.eq(user.id));

        List<UserQueryDto.Response> content = queryFactory
                .select(new QUserQueryDto_Response(
                        user.id,
                        user.userNickname,
                        user.userPhoneNum,
                        user.userBirth.stringValue(),
                        user.userGender,
                        user.marketingAgreeYn,
                        user.createdAt.stringValue(),
                        reportedCount,
                        reviewCount,
                        checkCount
                ))
                .from(user)
                .fetch();

        System.out.println(content);
    }

}