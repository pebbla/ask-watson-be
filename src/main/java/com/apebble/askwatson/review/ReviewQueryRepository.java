package com.apebble.askwatson.review;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.apebble.askwatson.review.QReview.review;
import static com.apebble.askwatson.user.QUser.user;

/**
 * 화면용 쿼리
 */
@Repository
public class ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReviewQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * 회원별 리뷰목록 조회
     */
    public Page<ReviewQueryDto.Response> getReviewsByUserId(Long userId, Pageable pageable) {
        List<ReviewQueryDto.Response> content = queryFactory
                .select(new QReviewQueryDto_Response(
                        review.id,
                        review.difficulty,
                        review.timeTaken,
                        review.usedHintNum,
                        review.rating,
                        review.deviceRatio,
                        review.activity,
                        review.content,
                        review.user.id,
                        review.user.userNickname
                ))
                .from(review)
                .join(review.user, user)
                .where(review.user.id.eq(userId))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(review)
                .where(review.user.id.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }


    /**
     * 테마별 리뷰목록 조회
     */
    public Page<ReviewQueryDto.Response> getReviewsByThemeId(Long themeId, Pageable pageable) {
        List<ReviewQueryDto.Response> content = queryFactory
                .select(new QReviewQueryDto_Response(
                        review.id,
                        review.difficulty,
                        review.timeTaken,
                        review.usedHintNum,
                        review.rating,
                        review.deviceRatio,
                        review.activity,
                        review.content,
                        review.user.id,
                        review.user.userNickname
                ))
                .from(review)
                .leftJoin(review.user, user)
                .where(review.theme.id.eq(themeId))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(review)
                .where(review.theme.id.eq(themeId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
