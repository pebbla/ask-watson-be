package com.apebble.askwatson.cafe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


import static com.apebble.askwatson.cafe.QCafe.*;
import static com.apebble.askwatson.cafe.location.QLocation.location;

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

}
