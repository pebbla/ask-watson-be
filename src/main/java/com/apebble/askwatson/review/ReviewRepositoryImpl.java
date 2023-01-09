package com.apebble.askwatson.review;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.apebble.askwatson.review.QReview.review;
import static com.apebble.askwatson.user.QUser.user;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Review> findByIdWithUser(Long id) {
        Review findReview = queryFactory
                .selectFrom(review)
                .leftJoin(review.user, user).fetchJoin()
                .where(review.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(findReview);
    }

}
