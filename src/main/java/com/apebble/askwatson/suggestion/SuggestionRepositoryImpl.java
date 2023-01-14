package com.apebble.askwatson.suggestion;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.apebble.askwatson.cafe.QCafe.cafe;
import static com.apebble.askwatson.suggestion.QSuggestion.suggestion;
import static com.apebble.askwatson.user.QUser.user;

public class SuggestionRepositoryImpl implements SuggestionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SuggestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Suggestion> findByIdWithCafeAndUser(Long id) {
        Suggestion findSuggestion = queryFactory
                .selectFrom(suggestion)
                .join(suggestion.cafe, cafe).fetchJoin()
                .join(suggestion.user, user).fetchJoin()
                .where(suggestion.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(findSuggestion);
    }
}
