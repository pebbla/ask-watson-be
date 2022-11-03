package com.apebble.askwatson.cafe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CafeJpaRepository extends JpaRepository<Cafe, Long> {

    @Query(value = "select c from Cafe c join fetch c.location where c.id=:id")
    Optional<Cafe> findByIdWithLocation(Long id);

    @Query(value = "select c from Cafe c join fetch c.location")
    List<Cafe> findAllCafes();

    @Query(value = "select c from Cafe c join fetch c.location where c.isAvailable=:isAvailable",
        countQuery = "select count(c) from Cafe c inner join c.location where c.isAvailable=:isAvailable")
    Page<Cafe> findCafesByIsAvailable(@Param("isAvailable") boolean isAvailable, Pageable pageable);

    @Query(value = "select c from Cafe c join fetch c.location l " +
            "where (:#{#options.searchWord} is null or (c.cafeName like %:#{#options.searchWord}% or c.address like %:#{#options.searchWord}%))" +
            "and (:#{#options.locationId} is null or l.id=:#{#options.locationId}) " +
            "and (:#{#options.isEnglishPossible} is null or c.isEnglishPossible=:#{#options.isEnglishPossible}) " +
            "and c.isAvailable=:isAvailable",
    countQuery = "select count(c) from Cafe c inner join c.location l " +
            "where (:#{#options.searchWord} is null or (c.cafeName like %:#{#options.searchWord}% or c.address like %:#{#options.searchWord}%))" +
            "and (:#{#options.locationId} is null or l.id=:#{#options.locationId}) " +
            "and (:#{#options.isEnglishPossible} is null or c.isEnglishPossible=:#{#options.isEnglishPossible}) " +
            "and c.isAvailable=:isAvailable")
    Page<Cafe> findCafesByOptionsAndIsAvailable(@Param("options") CafeSearchOptions options, @Param("isAvailable") boolean isAvailable, Pageable pageable);


    @Query(value = "select c from Cafe c join fetch c.location l where :searchWord is null or ((c.cafeName like %:searchWord% or c.address like %:searchWord%) " +
            "and (l is null or l.state like %:searchWord% or l.city like %:searchWord%))")
    List<Cafe> findCafesBySearchWord(@Param("searchWord") String searchWord);

}



