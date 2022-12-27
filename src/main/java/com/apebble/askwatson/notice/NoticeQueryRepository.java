package com.apebble.askwatson.notice;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.notice.QNotice.notice;

@Repository
public class NoticeQueryRepository {
    private final JPAQueryFactory queryFactory;

    public NoticeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Notice> findNoticesBySearchWord(String searchWord) {
        return queryFactory
                .selectFrom(notice)
                .where(searchWordCond(searchWord))
                .fetch();
    }

    private BooleanExpression searchWordCond(String searchWord) {
        return (searchWord == null || searchWord.equals(""))
                ? alwaysTrue
                : notice.title.contains(searchWord)
                .or(notice.content.contains(searchWord));
    }

}
