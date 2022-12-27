package com.apebble.askwatson.heart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long>, HeartRepositoryCustom {

    Optional<Heart> findByUserIdAndThemeId(Long userId, Long themeId);

}

