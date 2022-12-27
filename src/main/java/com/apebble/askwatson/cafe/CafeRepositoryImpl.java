package com.apebble.askwatson.cafe;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


import static com.apebble.askwatson.cafe.QCafe.*;
import static com.apebble.askwatson.cafe.location.QLocation.location;
import static com.apebble.askwatson.comm.util.QueryDslUtils.*;

public class CafeRepositoryImpl implements CafeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CafeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Cafe> findByIdWithLocation(Long id) {
        Cafe findCafe = queryFactory
                .selectFrom(cafe)
                .join(cafe.location, location).fetchJoin()
                .where(cafe.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(findCafe);
    }

    @Override
    public Page<Cafe> findAvailableCafesByOptions(CafeSearchOptions options, Pageable pageable) {
        return (options == null)
                ? findAllAvailableCafes(pageable)
                : findAvailableCafesByCond(options, pageable);
    }

    private Page<Cafe> findAllAvailableCafes(Pageable pageable) {
        List<Cafe> content = queryFactory
                .selectFrom(cafe)
                .join(cafe.location, location).fetchJoin()
                .where(availableCon(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(cafe)
                .where(availableCon(true));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Page<Cafe> findAvailableCafesByCond(CafeSearchOptions options, Pageable pageable) {
        List<Cafe> content = queryFactory
                .selectFrom(cafe)
                .join(cafe.location, location).fetchJoin()
                .where(availableCon(true),
                        searchWordCond(options.getSearchWord()),
                        locationCond(options.getLocationId()),
                        englishPossibleCond(options.getIsEnglishPossible()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(cafe.count())
                .from(cafe)
                .join(cafe.location, location)
                .where(availableCon(true),
                        searchWordCond(options.getSearchWord()),
                        locationCond(options.getLocationId()),
                        englishPossibleCond(options.getIsEnglishPossible()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Cafe> findCafesBySearchWord(String searchWord) {
        return queryFactory
                .selectFrom(cafe)
                .join(cafe.location, location).fetchJoin()
                .where(availableCon(true),
                        searchWordCond(searchWord))
                .fetch();
    }

    private BooleanExpression availableCon(boolean isAvailable) {
        return cafe.isAvailable.eq(isAvailable);
    }

    private BooleanExpression searchWordCond(String searchWord) {
        return (searchWord == null || searchWord.equals(""))
                ? alwaysTrue
                : cafe.cafeName.contains(searchWord)
                    .or(cafe.address.contains(searchWord))
                    .or(cafe.location.city.contains(searchWord))
                    .or(cafe.location.state.contains(searchWord));
    }

    private BooleanExpression locationCond(Long locationId) {
        return (locationId == null)
                ? alwaysTrue
                : cafe.location.id.eq(locationId);
    }

    private BooleanExpression englishPossibleCond(Boolean isEnglishPossible) {
        return (isEnglishPossible == null)
                ? alwaysTrue
                : cafe.isEnglishPossible.eq(isEnglishPossible);
    }
}
