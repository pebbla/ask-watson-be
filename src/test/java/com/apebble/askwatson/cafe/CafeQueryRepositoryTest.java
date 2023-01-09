package com.apebble.askwatson.cafe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.apebble.askwatson.cafe.QCafe.cafe;
import static com.apebble.askwatson.cafe.location.QLocation.location;

@SpringBootTest
@Transactional
class CafeQueryRepositoryTest {
    @PersistenceContext EntityManager em;
    JPAQueryFactory queryFactory;

    @Test
    public void findAllAvailableCafes() {
        queryFactory = new JPAQueryFactory(em);
        List<CafeQueryDto.Response> content = queryFactory
                .select(new QCafeQueryDto_Response(
                        cafe.id,
                        cafe.cafeName,
                        cafe.cafePhoneNum,
                        cafe.location.id,
                        cafe.location.state,
                        cafe.location.city,
                        cafe.website,
                        cafe.address,
                        cafe.imageUrl,
                        cafe.geography,
                        cafe.reviewCount,
                        cafe.rating,
                        cafe.isEnglishPossible,
                        cafe.isAvailable
                ))
                .from(cafe)
                .join(cafe.location, location)
                .fetch();

        System.out.println(content);
    }

}