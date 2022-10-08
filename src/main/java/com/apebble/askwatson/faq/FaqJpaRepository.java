package com.apebble.askwatson.faq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FaqJpaRepository extends JpaRepository<Faq, Long> {
    @Query(value = "select f from Faq f where :searchWord is null or (f.title like %:searchWord% or f.content like %:searchWord%)")
    List<Faq> findFaqsBySearchWord(@Param("searchWord") String searchWord);
}
