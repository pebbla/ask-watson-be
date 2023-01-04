package com.apebble.askwatson.check;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.apebble.askwatson.cafe.QCafe.cafe;
import static com.apebble.askwatson.cafe.location.QLocation.location;
import static com.apebble.askwatson.check.QCheck.check;
import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.heart.QHeart.heart;
import static com.apebble.askwatson.theme.QTheme.theme;

@SpringBootTest
@Transactional
class CheckQueryRepositoryTest {
    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Test
    public void getChecksByUser() {
        queryFactory = new JPAQueryFactory(em);
        Long userId = 1L;
        List<CheckQueryDto.Response> content = queryFactory
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
                .leftJoin(check).on(check.theme.id.eq(theme.id))
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .leftJoin(heart).on(heart.user.id.eq(userId), heart.theme.id.eq(theme.id))
                .where(check.user.id.eq(userId))
                .fetch();

        System.out.println(content);

    }
}