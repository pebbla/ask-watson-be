package com.apebble.askwatson.cafe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CafeRepositoryCustom {

    Optional<Cafe> findByIdWithLocation(Long id);
    Page<Cafe> findAvailableCafesByOptions(CafeSearchOptions options, Pageable pageable);
    List<Cafe> findCafesBySearchWord(String searchWord);

}
