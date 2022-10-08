package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeJpaRepository extends JpaRepository<Theme, Long> {
    Page<Theme> findThemesByIsAvailable(boolean isAvailable, Pageable pageable);

    List<Theme> findThemesByCafe(Cafe cafe);

    @Query(value = "select t from Theme t where (:#{#options.searchWord} is null or (t.themeName like %:#{#options.searchWord}% or t.category.categoryName like %:#{#options.searchWord}% or t.themeExplanation like %:#{#options.searchWord}% or t.cafe.cafeName like %:#{#options.searchWord}%)) " +
            "and (:#{#options.locationId} is null or t.cafe.location is null or t.cafe.location.id=:#{#options.locationId}) " +
            "and (:#{#options.categoryId} is null or t.category.id=:#{#options.categoryId}) " +
            "and (:#{#options.minNumPeople} is null or (:#{#options.minNumPeople} <= t.minNumPeople)) " +
            "and (:#{#options.difficultyRangeFrom} is null or (:#{#options.difficultyRangeFrom} <= t.difficulty)) " +
            "and (:#{#options.difficultyRangeTo} is null or (t.difficulty < :#{#options.difficultyRangeTo})) " +
            "and (:#{#options.deviceRatioRangeFrom} is null or (:#{#options.deviceRatioRangeFrom} <= t.deviceRatio)) " +
            "and (:#{#options.deviceRatioRangeTo} is null or (t.deviceRatio < :#{#options.deviceRatioRangeTo})) " +
            "and (:#{#options.activityRangeFrom} is null or (:#{#options.activityRangeFrom} <= t.activity)) " +
            "and (:#{#options.activityRangeTo} is null or (t.activity < :#{#options.activityRangeTo})) " +
            "and (:#{#options.timeLimitRangeFrom} is null or (:#{#options.timeLimitRangeFrom} <= t.timeLimit)) " +
            "and (:#{#options.timeLimitRangeTo} is null or (t.timeLimit < :#{#options.timeLimitRangeTo})) " +
            "and t.isAvailable=:isAvailable" )
    Page<Theme> findThemesByOptionsAndIsAvailable(@Param("options") ThemeSearchOptions options, @Param("isAvailable") boolean isAvailable, Pageable pageable);

    @Query(value = "select t from Theme t where :searchWord is null or (t.themeName like %:searchWord% or t.themeExplanation like %:searchWord%  or t.cafe.cafeName like %:searchWord% or t.cafe.address like %:searchWord% or t.cafe.location.state like %:searchWord% or t.cafe.location.city like %:searchWord% or t.category.categoryName like %:searchWord%)")
    List<Theme> findThemesBySearchWord(@Param("searchWord") String searchWord);
}
