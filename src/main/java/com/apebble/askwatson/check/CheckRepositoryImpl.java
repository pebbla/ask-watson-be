package com.apebble.askwatson.check;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.check.QCheck.check;
import static com.apebble.askwatson.theme.QTheme.theme;

public class CheckRepositoryImpl implements CheckRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CheckRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Check> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(check)
                .join(check.theme, theme).fetchJoin()
                .where(check.user.id.eq(userId))
                .fetch();
    }
}
