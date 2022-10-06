package com.apebble.askwatson.heart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartJpaRepository extends JpaRepository<Heart, Long> {
    List<Heart> findByUserId(Long userId);

    Optional<Heart> findByUserIdAndThemeId(Long userId, Long themeId);
}

