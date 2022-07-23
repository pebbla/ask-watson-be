package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.theme.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeJpaRepository extends JpaRepository<Theme, Long> {
    List<Theme> findThemesByCafe(Cafe cafe);

    @Query(value = "select t from Theme t where (:cafe is null or t.cafe=:cafe) " +
            "and (:location is null or t.cafe.location=:location) " +
            "and (:category is null or t.category=:category) " +
            "and (:minDifficulty is null or (:minDifficulty <= t.difficulty and t.difficulty <= :maxDifficulty)) ")
    List<Theme> findThemesByFilters(@Param("cafe") Cafe cafe, @Param("location") Location location, @Param("category") Category category,
                                    @Param("minDifficulty") Double minDifficulty, @Param("maxDifficulty") Double maxDifficulty);

}
