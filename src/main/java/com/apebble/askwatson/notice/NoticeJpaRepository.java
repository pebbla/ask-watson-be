package com.apebble.askwatson.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long> {
    @Query(value = "select n from Notice n where :searchWord is null or (n.title like %:searchWord% or n.content like %:searchWord%)")
    List<Notice> findNoticesBySearchWord(String searchWord);
    
}
