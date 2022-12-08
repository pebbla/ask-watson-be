package com.apebble.askwatson.heart;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeartJpaRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserIdAndThemeId(Long userId, Long themeId);

    @Query(value = "select h from Heart h join fetch h.theme t join fetch t.cafe cf join fetch t.category c where h.user.id=:userId")
    List<Heart> findByUserIdWithCategory(@Param("userId") Long userId);

}

