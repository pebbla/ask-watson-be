package com.apebble.askwatson.cafe;

import java.util.Optional;

public interface CafeRepositoryCustom {

    Optional<Cafe> findByIdWithLocation(Long id);

}
