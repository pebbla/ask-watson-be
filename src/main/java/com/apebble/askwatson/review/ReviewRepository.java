package com.apebble.askwatson.review;

import java.util.List;
import java.util.Optional;

import com.apebble.askwatson.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import com.apebble.askwatson.user.User;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    List<Review> findByUser(User user);

    List<Review> findByThemeId(Long themeId);

    Optional<Review> findByUserAndTheme(User user, Theme theme);

    int countByUser(User user);

}
