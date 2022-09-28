package com.apebble.askwatson.heart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartJpaRepository extends JpaRepository<Heart, Long> {
    List<Heart> findByUserId(Long userId);

    Heart findByUserIdAndThemeId(Long userId, Long themeId);
}

