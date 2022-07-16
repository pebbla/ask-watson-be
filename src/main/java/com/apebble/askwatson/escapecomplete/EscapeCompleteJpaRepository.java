package com.apebble.askwatson.escapecomplete;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscapeCompleteJpaRepository extends JpaRepository<EscapeComplete, Long> {
    List<EscapeComplete> findByUserId(Long userId);
}
