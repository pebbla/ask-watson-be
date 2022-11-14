package com.apebble.askwatson.review;

import java.util.List;
import java.util.Optional;

import com.apebble.askwatson.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import com.apebble.askwatson.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r from Review r join fetch r.user where r.id=:id")
    Optional<Review> findByIdWithUser(@Param("id") Long id);

    List<Review> findByUser(User user);

    List<Review> findByThemeId(Long themeId);

    Optional<Review> findByUserAndTheme(User user, Theme theme);

    int countByUser(User user);

}
