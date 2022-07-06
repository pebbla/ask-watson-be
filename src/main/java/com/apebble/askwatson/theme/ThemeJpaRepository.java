package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThemeJpaRepository extends JpaRepository<Theme, Long> {
    List<Theme> findThemesByCafe(Cafe cafe);
}
