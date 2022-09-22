package com.apebble.askwatson.review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apebble.askwatson.user.User;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);

    List<Review> findByThemeId(Long themeId);

    int countByUser(User user);
}
