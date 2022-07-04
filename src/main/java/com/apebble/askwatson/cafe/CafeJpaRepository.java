package com.apebble.askwatson.cafe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeJpaRepository extends JpaRepository<Cafe, Long> {
}
