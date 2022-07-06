package com.apebble.askwatson.faq;

import com.apebble.askwatson.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqJpaRepository extends JpaRepository<Faq, Long> {
}
