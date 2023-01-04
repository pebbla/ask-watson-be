package com.apebble.askwatson.check;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
 * 화면용 쿼리
 */
@Repository
public class CheckQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CheckQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 회원별 탈출완료 리스트 조회
     */
    public List<CheckQueryDto.Response> getChecksByUserId(Long userId) {
        return queryFactory
                .select(new QCheckQueryDto_Response(
                        check.id,
                        check.checkDt.stringValue(),
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
                        heart.isNotNull(),
                        alwaysTrue
                ))
                .from(theme)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .leftJoin(check).on(check.theme.id.eq(theme.id))
                .leftJoin(heart).on(heart.user.id.eq(userId), heart.theme.id.eq(theme.id))
                .where(check.user.id.eq(userId))
                .fetch();
    }

}
