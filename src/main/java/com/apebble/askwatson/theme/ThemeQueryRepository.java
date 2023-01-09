package com.apebble.askwatson.theme;

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
import static com.apebble.askwatson.check.QCheck.check;
import static com.apebble.askwatson.comm.util.QueryDslUtils.alwaysTrue;
import static com.apebble.askwatson.heart.QHeart.heart;
import static com.apebble.askwatson.theme.QTheme.theme;
import static com.apebble.askwatson.theme.category.QCategory.category;


/**
 * 테마 화면용 쿼리
 */
@Repository
public class ThemeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ThemeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * [모바일앱] 유저 테마 단건 조회
     */
    public ThemeQueryDto.Response getOneThemeByUser(Long userId, Long themeId) {
        return queryFactory
                .select(new QThemeQueryDto_Response(
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id,
                        heart.isNotNull(),
                        check.isNotNull()
                ))
                .from(theme)
                .join(theme.category, category)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .leftJoin(heart).on(heart.user.id.eq(userId), heart.theme.id.eq(theme.id))
                .leftJoin(check).on(check.user.id.eq(userId), check.theme.id.eq(theme.id))
                .where(theme.id.eq(themeId))
                .fetchOne();
    }


    /**
     * [모바일앱] 카페별 테마 목록 조회
     */
    public List<ThemeQueryDto.Response> getThemesByCafe(Long userId, Long cafeId) {
        return queryFactory
                .select(new QThemeQueryDto_Response(
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id,
                        heart.isNotNull(),
                        check.isNotNull()
                ))
                .from(theme)
                .join(theme.category, category)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .leftJoin(heart).on(heart.user.id.eq(userId), heart.theme.id.eq(theme.id))
                .leftJoin(check).on(check.user.id.eq(userId), check.theme.id.eq(theme.id))
                .where(theme.cafe.id.eq(cafeId))
                .fetch();
    }


    /**
     * [모바일앱] 유저 테마 목록 검색 쿼리
     */
    public Page<ThemeQueryDto.Response> getThemePageByUser(Long userId, ThemeSearchOptions options, Pageable pageable) {
        return (options == null)
                ? findAvailableThemesByUser(userId, pageable)
                : findAvailableThemesByUserByCond(userId, options, pageable);
    }

    private Page<ThemeQueryDto.Response> findAvailableThemesByUser(Long userId, Pageable pageable) {
        List<ThemeQueryDto.Response> content = queryFactory
                .select(new QThemeQueryDto_Response(
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id,
                        heart.isNotNull(),
                        check.isNotNull()
                ))
                .from(theme)
                .join(theme.category, category)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .leftJoin(heart).on(heart.user.id.eq(userId), heart.theme.id.eq(theme.id))
                .leftJoin(check).on(check.user.id.eq(userId), check.theme.id.eq(theme.id))
                .where(availableCon(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(theme)
                .where(availableCon(true));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Page<ThemeQueryDto.Response> findAvailableThemesByUserByCond(Long userId, ThemeSearchOptions options, Pageable pageable) {
        List<ThemeQueryDto.Response> content = queryFactory
                .select(new QThemeQueryDto_Response(
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id,
                        heart.isNotNull(),
                        check.isNotNull()
                ))
                .from(theme)
                .join(theme.category, category)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .leftJoin(heart).on(heart.user.id.eq(userId), heart.theme.id.eq(theme.id))
                .leftJoin(check).on(check.user.id.eq(userId), check.theme.id.eq(theme.id))
                .where(themeSearchCon(options))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(theme)
                .where(themeSearchCon(options));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression themeSearchCon(ThemeSearchOptions options) {
        return availableCon(true)
                .and(searchWordCond(options.getSearchWord()))
                .and(locationCond(options.getLocationId()))
                .and(categoryCond(options.getCategoryId()))
                .and(minNumPeopleCond(options.getMinNumPeople()))
                .and(difficultyGoe(options.getDifficultyRangeFrom()))
                .and(difficultyLoe(options.getDifficultyRangeTo()))
                .and(deviceRatioGoe(options.getDeviceRatioRangeFrom()))
                .and(deviceRatioLoe(options.getDeviceRatioRangeTo()))
                .and(activityGoe(options.getActivityRangeFrom()))
                .and(activityLoe(options.getActivityRangeTo()))
                .and(timeLimitGoe(options.getTimeLimitRangeFrom()))
                .and(timeLimitLoe(options.getTimeLimitRangeTo()));
    }


    /**
     * [관리자웹] 테마 검색 쿼리
     */
    public List<ThemeQueryDto.WebResponse> getThemeList(String searchWord, Boolean sortByUpdateYn) {
        if(sortByUpdateYn != null && sortByUpdateYn) {
            return findThemesSortByUpdate(searchWord);
        } else {
            return findThemes(searchWord);
        }
    }

    private List<ThemeQueryDto.WebResponse> findThemesSortByUpdate(String searchWord) {
        List<ThemeQueryDto.WebResponse> result = new ArrayList<>();

        List<ThemeQueryDto.WebResponse> unmodifiedThemes = findUnmodifiedThemes(searchWord);
        List<ThemeQueryDto.WebResponse> modifiedThemes = sortByUpdate(findModifiedThemes(searchWord));

        result.addAll(unmodifiedThemes);
        result.addAll(modifiedThemes);

        return result;
    }

    private List<ThemeQueryDto.WebResponse> findUnmodifiedThemes(String searchWord) {
        return queryFactory
                .select(new QThemeQueryDto_WebResponse(
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id
                ))
                .from(theme)
                .join(theme.category, category)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .where(searchWordCond(searchWord),
                        theme.modifiedAt.isNull())
                .fetch();
    }

    private List<ThemeQueryDto.WebResponse> findModifiedThemes(String searchWord) {
        return queryFactory
                .select(new QThemeQueryDto_WebResponse(
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id
                ))
                .from(theme)
                .join(theme.category, category)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .where(searchWordCond(searchWord),
                        theme.modifiedAt.isNotNull())
                .fetch();
    }

    private List<ThemeQueryDto.WebResponse> findThemes(String searchWord) {
        return queryFactory
                .select(new QThemeQueryDto_WebResponse(
                        theme.id,
                        theme.themeName,
                        theme.themeExplanation,
                        theme.category.id,
                        theme.category.categoryName,
                        theme.difficulty,
                        theme.timeLimit,
                        theme.minNumPeople,
                        theme.price,
                        theme.reservationUrl,
                        theme.imageUrl,
                        theme.heartCount,
                        theme.escapeCount,
                        theme.reviewCount,
                        theme.rating,
                        theme.deviceRatio,
                        theme.activity,
                        theme.isAvailable,
                        theme.cafe.id,
                        theme.cafe.cafeName,
                        theme.cafe.cafePhoneNum,
                        theme.cafe.location.id
                ))
                .from(theme)
                .join(theme.category, category)
                .join(theme.cafe, cafe)
                .join(cafe.location, location)
                .where(searchWordCond(searchWord))
                .fetch();
    }

    private List<ThemeQueryDto.WebResponse> sortByUpdate(List<ThemeQueryDto.WebResponse> themeList) {
        List<ThemeQueryDto.WebResponse> nullColumnList = new ArrayList<>();
        List<ThemeQueryDto.WebResponse> nonNullColumnList = new ArrayList<>();

        themeList.forEach(theme -> {
            if(theme.getThemeName() == null || theme.getThemeName().equals("") ||
                    theme.getThemeExplanation() == null || theme.getThemeExplanation().equals("") ||
                    theme.getTimeLimit() == null || theme.getTimeLimit().equals(0) ||
                    theme.getMinNumPeople() == null || theme.getMinNumPeople().equals(0) ||
                    theme.getPrice() == null || theme.getPrice().equals(0) ||
                    theme.getImageUrl() == null || theme.getImageUrl().equals("") ||
                    theme.getReservationUrl() == null || theme.getReservationUrl().equals("") ||
                    theme.getCategory() == null)
                nullColumnList.add(theme);
            else nonNullColumnList.add(theme);
        });

        List<ThemeQueryDto.WebResponse> result = new ArrayList<>();
        result.addAll(nullColumnList);
        result.addAll(nonNullColumnList);

        return result;
    }

    private BooleanExpression availableCon(boolean isAvailable) {
        return theme.isAvailable.eq(isAvailable);
    }

    private BooleanExpression searchWordCond(String searchWord) {
        return (searchWord == null || searchWord.equals(""))
                ? alwaysTrue
                : theme.themeName.contains(searchWord)
                .or(theme.category.categoryName.contains(searchWord))
                .or(theme.themeExplanation.contains(searchWord))
                .or(theme.cafe.cafeName.contains(searchWord));
    }

    private BooleanExpression locationCond(Long locationId) {
        return (locationId != null) ? theme.cafe.location.id.eq(locationId) : alwaysTrue;
    }

    private BooleanExpression categoryCond(Long categoryId) {
        return (categoryId != null) ? theme.category.id.eq(categoryId) : alwaysTrue;
    }

    private BooleanExpression minNumPeopleCond(Integer minNumPeople) {
        return (minNumPeople != null) ? theme.minNumPeople.eq(minNumPeople) : alwaysTrue;
    }

    private BooleanExpression difficultyGoe(Double difficultyFrom) {
        return (difficultyFrom != null) ? theme.difficulty.goe(difficultyFrom) : alwaysTrue;
    }

    private BooleanExpression difficultyLoe(Double difficultyTo) {
        return (difficultyTo != null) ? theme.difficulty.loe(difficultyTo) : alwaysTrue;
    }

    private BooleanExpression deviceRatioGoe(Double deviceRatioFrom) {
        return (deviceRatioFrom != null) ? theme.deviceRatio.goe(deviceRatioFrom) : alwaysTrue;
    }

    private BooleanExpression deviceRatioLoe(Double deviceRatioTo) {
        return (deviceRatioTo != null) ? theme.deviceRatio.loe(deviceRatioTo) : alwaysTrue;
    }

    private BooleanExpression activityGoe(Double activityFrom) {
        return (activityFrom != null) ? theme.activity.goe(activityFrom) : alwaysTrue;
    }

    private BooleanExpression activityLoe(Double activityTo) {
        return (activityTo != null) ? theme.activity.loe(activityTo) : alwaysTrue;
    }

    private BooleanExpression timeLimitGoe(Integer timeLimitFrom) {
        return (timeLimitFrom != null) ? theme.timeLimit.goe(timeLimitFrom) : alwaysTrue;
    }

    private BooleanExpression timeLimitLoe(Integer timeLimitTo) {
        return (timeLimitTo != null) ? theme.timeLimit.loe(timeLimitTo) : alwaysTrue;
    }

}
