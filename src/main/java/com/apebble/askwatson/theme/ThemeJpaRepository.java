package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeJpaRepository extends JpaRepository<Theme, Long> {
    List<Theme> findThemesByCafe(Cafe cafe);

    @Query(value = "select t from Theme t where (:#{#options.companyId} is null or t.cafe.company.id=:#{#options.companyId}) " +
            "and (:#{#options.locationId} is null or t.cafe.location.id=:#{#options.locationId}) " +
            "and (:#{#options.categoryId} is null or t.category.id=:#{#options.categoryId}) " +
            "and (:#{#options.difficultyRangeFrom} is null or (:#{#options.difficultyRangeFrom} <= t.difficulty)) " +
            "and (:#{#options.difficultyRangeTo} is null or (t.difficulty <= :#{#options.difficultyRangeTo})) " +
            "and (:#{#options.priceRangeFrom} is null or (:#{#options.priceRangeFrom} <= t.price)) " +
            "and (:#{#options.priceRangeTo} is null or (t.price <= :#{#options.priceRangeTo})) " +
            "and (:#{#options.deviceRatioRangeFrom} is null or (:#{#options.deviceRatioRangeFrom} <= t.deviceRatio)) " +
            "and (:#{#options.deviceRatioRangeTo} is null or (t.deviceRatio <= :#{#options.deviceRatioRangeTo})) " +
            "and (:#{#options.activityRangeFrom} is null or (:#{#options.activityRangeFrom} <= t.activity)) " +
            "and (:#{#options.activityRangeTo} is null or (t.activity <= :#{#options.activityRangeTo})) " +
            "and (:#{#options.minNumPeople} is null or (:#{#options.minNumPeople} <= t.minNumPeople)) ")
    Page<Theme> findThemesByOptions(@Param("options") ThemeSearchOptions options, Pageable pageable);
}
