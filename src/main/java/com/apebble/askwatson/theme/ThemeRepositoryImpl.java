package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.apebble.askwatson.theme.QTheme.theme;
import static com.apebble.askwatson.theme.category.QCategory.category;

public class ThemeRepositoryImpl implements ThemeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ThemeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Theme> findByIdWithCategory(Long id) {
        Theme findTheme = queryFactory
                .selectFrom(theme)
                .join(theme.category, category).fetchJoin()
                .where(theme.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(findTheme);
    }

    @Override
    public List<Theme> findThemesByCafe(Cafe cafe) {
        return queryFactory
                .selectFrom(theme)
                .join(theme.category, category).fetchJoin()
                .where(theme.cafe.eq(cafe))
                .fetch();
    }
}
