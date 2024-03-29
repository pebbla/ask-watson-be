package com.apebble.askwatson.heart;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.cafe.QCafe.cafe;
import static com.apebble.askwatson.cafe.location.QLocation.location;
import static com.apebble.askwatson.check.QCheck.check;
import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.heart.QHeart.heart;
import static com.apebble.askwatson.theme.QTheme.theme;


/**
 * 좋아요 화면용 쿼리
 */
@Repository
public class HeartQueryRepository {

    private final JPAQueryFactory queryFactory;

    public HeartQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 회원별 좋아요 리스트 조회
     */
    public Page<HeartQueryDto.Response> getHeartsByUserId(Long userId, Pageable pageable) {
        List<HeartQueryDto.Response> content = queryFactory
                .select(new QHeartQueryDto_Response(
                        heart.id,
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id,
                        alwaysTrue,
                        check.isNotNull()
                ))
                .from(theme)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .leftJoin(check).on(check.user.id.eq(userId), check.theme.id.eq(theme.id))
                .rightJoin(heart).on(heart.theme.id.eq(theme.id))
                .where(heart.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(heart)
                .where(heart.user.id.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

}
