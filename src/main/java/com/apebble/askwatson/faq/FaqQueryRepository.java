package com.apebble.askwatson.faq;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.faq.QFaq.faq;

/**
 * 자주묻는질문 화면용 쿼리
 */
@Repository
public class FaqQueryRepository {
    private final JPAQueryFactory queryFactory;

    public FaqQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * 자주묻는질문 목록 전체 조회
     */
    public List<FaqQueryDto.Response> findFaqsBySearchWord(String searchWord) {
        return queryFactory
                .select(new QFaqQueryDto_Response(
                        faq.id,
                        faq.title,
                        faq.content
                ))
                .from(faq)
                .where(searchWordCond(searchWord))
                .fetch();
    }

    private BooleanExpression searchWordCond(String searchWord) {
        return (searchWord == null || searchWord.equals(""))
                ? alwaysTrue
                : faq.title.contains(searchWord)
                .or(faq.content.contains(searchWord));
    }

}
