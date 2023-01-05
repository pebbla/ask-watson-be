package com.apebble.askwatson.check;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckRepository extends JpaRepository<Check, Long>, CheckRepositoryCustom {

    Optional<Check> findByUserIdAndThemeId(Long userId, Long themeId);

}
