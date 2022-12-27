package com.apebble.askwatson.review;

import java.util.Optional;

public interface ReviewRepositoryCustom {

    Optional<Review> findByIdWithUser(Long id);

}
