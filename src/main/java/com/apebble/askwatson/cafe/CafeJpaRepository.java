package com.apebble.askwatson.cafe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CafeJpaRepository extends JpaRepository<Cafe, Long> {
    @Query(value = "select c from Cafe c where (:#{#options.searchWord} is null or (c.cafeName like %:#{#options.searchWord}% or c.address like %:#{#options.searchWord}%))" +
            "and (:#{#options.locationId} is null or c.location.id=:#{#options.locationId}) " +
            "and (:#{#options.isEnglishPossible} is null or c.isEnglishPossible=:#{#options.isEnglishPossible}) " )
    Page<Cafe> findCafesByOptions(@Param("options") CafeSearchOptions options, Pageable pageable);


    @Query(value = "select c from Cafe c where :searchWord is null or (c.cafeName like %:searchWord% or c.address like %:searchWord%  or c.location.state like %:searchWord% or c.location.city like %:searchWord%)")
    List<Cafe> findCafesBySearchWord(String searchWord);
}
