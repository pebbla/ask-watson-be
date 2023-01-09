package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;

import java.util.List;
import java.util.Optional;

public interface ThemeRepositoryCustom {

    Optional<Theme> findByIdWithCategory(Long id);

    List<Theme> findThemesByCafe(Cafe cafe);

}
