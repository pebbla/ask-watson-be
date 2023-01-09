package com.apebble.askwatson.heart;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.apebble.askwatson.heart.QHeart.heart;
import static com.apebble.askwatson.theme.QTheme.theme;

public class HeartRepositoryImpl implements HeartRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public HeartRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Heart> findByUserIdWithCategory(Long userId) {
        return queryFactory
                .selectFrom(heart)
                .join(heart.theme, theme).fetchJoin()
                .where(heart.user.id.eq(userId))
                .fetch();
    }

    @Override
    public Optional<Heart> findByIdWithTheme(Long id) {
        Heart findHeart = queryFactory
                .selectFrom(QHeart.heart)
                .join(QHeart.heart.theme, theme).fetchJoin()
                .where(QHeart.heart.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(findHeart);
    }

}
