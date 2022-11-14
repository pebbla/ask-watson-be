package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThemeJpaRepository extends JpaRepository<Theme, Long> {

    @Query(value = "select t from Theme t join fetch t.category where t.id=:id")
    Optional<Theme> findByIdWithCategory(@Param("id") Long id);

    @Query(value = "select t from Theme t join fetch t.category")
    List<Theme> findAllThemes();

    @Query(value = "select t from Theme t join fetch t.category where t.isAvailable=:isAvailable",
            countQuery = "select count(t) from Theme t inner join t.category where t.isAvailable=:isAvailable")
    Page<Theme> findThemesByIsAvailable(@Param("isAvailable") boolean isAvailable, Pageable pageable);

    @Query(value = "select t from Theme t join fetch t.category where t.cafe=:cafe")
    List<Theme> findThemesByCafe(@Param("cafe") Cafe cafe);

    @Query(value = "select t from Theme t join fetch t.category c " +
            "where (:#{#options.searchWord} is null or (t.themeName like %:#{#options.searchWord}% or t.category.categoryName like %:#{#options.searchWord}% or t.themeExplanation like %:#{#options.searchWord}% or t.cafe.cafeName like %:#{#options.searchWord}%)) " +
            "and (:#{#options.locationId} is null or t.cafe.location is null or t.cafe.location.id=:#{#options.locationId}) " +
            "and (:#{#options.categoryId} is null or c.id=:#{#options.categoryId}) " +
            "and (:#{#options.minNumPeople} is null or (:#{#options.minNumPeople} <= t.minNumPeople)) " +
            "and (:#{#options.difficultyRangeFrom} is null or (:#{#options.difficultyRangeFrom} <= t.difficulty)) " +
            "and (:#{#options.difficultyRangeTo} is null or (t.difficulty < :#{#options.difficultyRangeTo})) " +
            "and (:#{#options.deviceRatioRangeFrom} is null or (:#{#options.deviceRatioRangeFrom} <= t.deviceRatio)) " +
            "and (:#{#options.deviceRatioRangeTo} is null or (t.deviceRatio < :#{#options.deviceRatioRangeTo})) " +
            "and (:#{#options.activityRangeFrom} is null or (:#{#options.activityRangeFrom} <= t.activity)) " +
            "and (:#{#options.activityRangeTo} is null or (t.activity < :#{#options.activityRangeTo})) " +
            "and (:#{#options.timeLimitRangeFrom} is null or (:#{#options.timeLimitRangeFrom} <= t.timeLimit)) " +
            "and (:#{#options.timeLimitRangeTo} is null or (t.timeLimit < :#{#options.timeLimitRangeTo})) " +
            "and t.isAvailable=:isAvailable",
        countQuery = "select count(t) from Theme t inner join t.category c " +
            "where (:#{#options.searchWord} is null or (t.themeName like %:#{#options.searchWord}% or t.category.categoryName like %:#{#options.searchWord}% or t.themeExplanation like %:#{#options.searchWord}% or t.cafe.cafeName like %:#{#options.searchWord}%)) " +
            "and (:#{#options.locationId} is null or t.cafe.location is null or t.cafe.location.id=:#{#options.locationId}) " +
            "and (:#{#options.categoryId} is null or c.id=:#{#options.categoryId}) " +
            "and (:#{#options.minNumPeople} is null or (:#{#options.minNumPeople} <= t.minNumPeople)) " +
            "and (:#{#options.difficultyRangeFrom} is null or (:#{#options.difficultyRangeFrom} <= t.difficulty)) " +
            "and (:#{#options.difficultyRangeTo} is null or (t.difficulty < :#{#options.difficultyRangeTo})) " +
            "and (:#{#options.deviceRatioRangeFrom} is null or (:#{#options.deviceRatioRangeFrom} <= t.deviceRatio)) " +
            "and (:#{#options.deviceRatioRangeTo} is null or (t.deviceRatio < :#{#options.deviceRatioRangeTo})) " +
            "and (:#{#options.activityRangeFrom} is null or (:#{#options.activityRangeFrom} <= t.activity)) " +
            "and (:#{#options.activityRangeTo} is null or (t.activity < :#{#options.activityRangeTo})) " +
            "and (:#{#options.timeLimitRangeFrom} is null or (:#{#options.timeLimitRangeFrom} <= t.timeLimit)) " +
            "and (:#{#options.timeLimitRangeTo} is null or (t.timeLimit < :#{#options.timeLimitRangeTo})) " +
            "and t.isAvailable=:isAvailable")
    Page<Theme> findThemesByOptionsAndIsAvailable(@Param("options") ThemeSearchOptions options, @Param("isAvailable") boolean isAvailable, Pageable pageable);

    @Query(value = "select t from Theme t join fetch t.category c " +
            "where :searchWord is null or (t.themeName like %:searchWord% or t.themeExplanation like %:searchWord%  or t.cafe.cafeName like %:searchWord% or t.cafe.address like %:searchWord% or t.cafe.location.state like %:searchWord% or t.cafe.location.city like %:searchWord% or c.categoryName like %:searchWord%)")
    List<Theme> findThemesBySearchWord(@Param("searchWord") String searchWord);

}
