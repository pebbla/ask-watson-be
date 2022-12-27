package com.apebble.askwatson.faq;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.comm.util.QueryDslUtils.containsNullCheck;
import static com.apebble.askwatson.faq.QFaq.faq;

@Repository
public class FaqQueryRepository {
    private final JPAQueryFactory queryFactory;

    public FaqQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Faq> findFaqsBySearchWord(String searchWord) {
        return queryFactory
                .selectFrom(faq)
                .where(containsNullCheck(
                        searchWord,
                        faqContains(searchWord)))
                .fetch();
    }

    private BooleanExpression faqContains(String searchWord) {
        return faq.title.contains(searchWord)
                .or(faq.content.contains(searchWord));
    }
}
