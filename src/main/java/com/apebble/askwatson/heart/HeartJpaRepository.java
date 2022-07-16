package com.apebble.askwatson.heart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.apebble.askwatson.user.User;

public interface HeartJpaRepository extends JpaRepository<Heart, Long> {

    List<Heart> findByUserId(Long userId);

}

