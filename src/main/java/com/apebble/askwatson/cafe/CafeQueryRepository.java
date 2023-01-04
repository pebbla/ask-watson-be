package com.apebble.askwatson.cafe;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.apebble.askwatson.cafe.QCafe.cafe;
import static com.apebble.askwatson.cafe.location.QLocation.location;
import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;


/**
 * 화면용 쿼리
 */
@Repository
public class CafeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CafeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * [모바일앱] 유저 카페 검색 쿼리
     */
    public Page<CafeQueryDto.Response> getCafePage(CafeSearchOptions options, Pageable pageable) {
        return (options == null)
                ? findAvailableCafes(pageable)
                : findAvailableCafesByCond(options, pageable);
    }

    private Page<CafeQueryDto.Response> findAvailableCafes(Pageable pageable) {
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

    private Page<CafeQueryDto.Response> findAvailableCafesByCond(CafeSearchOptions options, Pageable pageable) {
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


    /**
     * [관리자웹] 카페 검색 쿼리
     */
    public List<CafeQueryDto.Response> getCafeList(String searchWord, Boolean sortByUpdateYn) {
        if(sortByUpdateYn!=null && sortByUpdateYn) {
            return findCafesSortByUpdate(searchWord);
        } else {
            return findCafes(searchWord);
        }
    }

    private List<CafeQueryDto.Response> findCafesSortByUpdate(String searchWord) {
        List<CafeQueryDto.Response> result = new ArrayList<>();

        List<CafeQueryDto.Response> unmodifiedCafes = findUnmodifiedCafes(searchWord);
        List<CafeQueryDto.Response> modifiedCafes = sortByUpdate(findModifiedCafes(searchWord));

        result.addAll(unmodifiedCafes);
        result.addAll(modifiedCafes);

        return result;
    }

    private List<CafeQueryDto.Response> findUnmodifiedCafes(String searchWord) {
        return queryFactory
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
                .where(searchWordCond(searchWord),
                        cafe.modifiedAt.isNull())
                .fetch();
    }

    private List<CafeQueryDto.Response> findModifiedCafes(String searchWord) {
        return queryFactory
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
                .where(searchWordCond(searchWord),
                        cafe.modifiedAt.isNotNull())
                .fetch();
    }

    private List<CafeQueryDto.Response> sortByUpdate(List<CafeQueryDto.Response> cafeList) {
        List<CafeQueryDto.Response> nullColumnList = new ArrayList<>();
        List<CafeQueryDto.Response> nonNullColumnList = new ArrayList<>();

        cafeList.forEach(cafe -> {
            if(cafe.getCafeName() == null || cafe.getCafeName().equals("") ||
                    cafe.getCafePhoneNum() == null || cafe.getCafePhoneNum().equals("") ||
                    cafe.getWebsite() == null || cafe.getWebsite().equals("") ||
                    cafe.getAddress() == null || cafe.getAddress().equals("") ||
                    cafe.getImageUrl() == null || cafe.getImageUrl().equals("") ||
                    cafe.getLocation() == null)
                nullColumnList.add(cafe);
            else nonNullColumnList.add(cafe);
        });

        List<CafeQueryDto.Response> result = new ArrayList<>();
        result.addAll(nullColumnList);
        result.addAll(nonNullColumnList);

        return result;
    }

    private List<CafeQueryDto.Response> findCafes(String searchWord) {
        return queryFactory
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
                .where(searchWordCond(searchWord))
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
