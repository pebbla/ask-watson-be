package com.apebble.askwatson.heart;

import java.util.List;
import java.util.Optional;

public interface HeartRepositoryCustom {

    List<Heart> findByUserIdWithCategory(Long userId);

    Optional<Heart> findByIdWithTheme(Long id);

}
