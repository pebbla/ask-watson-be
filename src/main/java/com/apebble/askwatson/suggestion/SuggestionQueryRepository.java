package com.apebble.askwatson.suggestion;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.apebble.askwatson.cafe.QCafe.cafe;
import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.suggestion.QSuggestion.suggestion;
import static com.apebble.askwatson.user.QUser.user;


/**
 * 신고 화면용 쿼리
 */
@Repository
public class SuggestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SuggestionQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * [관리자웹] 건의 목록 전체 조회
     */
    public List<SuggestionQueryDto.Response> getSuggestions(String searchWord, Boolean handledYn) {
        return queryFactory
                .select(new QSuggestionQueryDto_Response(
                        suggestion.id,
                        suggestion.content,
                        suggestion.createdAt.stringValue(),
                        suggestion.handledYn,
                        cafe.id,
                        cafe.cafeName,
                        user.id,
                        user.userNickname
                ))
                .from(suggestion)
                .join(suggestion.cafe, cafe)
                .leftJoin(suggestion.user, user)
                .where(searchWordCond(searchWord),
                        handledYnCond(handledYn))
                .fetch();
    }

    private BooleanExpression searchWordCond(String searchWord) {
        return (searchWord == null || searchWord.equals(""))
                ? alwaysTrue
                : cafeContains(searchWord)
                .or(userContains(searchWord))
                .or(suggestion.content.contains(searchWord));
    }

    private BooleanExpression cafeContains(String searchWord) {
        return suggestion.cafe.cafeName.contains(searchWord);
    }

    private BooleanExpression userContains(String searchWord) {
        return (suggestion.user == null)
                ? alwaysTrue
                : suggestion.user.userNickname.contains(searchWord);
    }

    private BooleanExpression handledYnCond(Boolean handledYn) {
        return (handledYn == null)
                ? alwaysTrue
                : suggestion.handledYn.eq(handledYn);
    }

}
