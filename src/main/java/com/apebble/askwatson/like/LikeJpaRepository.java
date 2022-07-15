package com.apebble.askwatson.like;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<Likes, Long> {

    List<Likes> findByUserId(Long userId);

}

