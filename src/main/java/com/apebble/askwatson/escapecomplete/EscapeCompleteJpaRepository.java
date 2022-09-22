package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscapeCompleteJpaRepository extends JpaRepository<EscapeComplete, Long> {
    List<EscapeComplete> findByUserId(Long userId);

    EscapeComplete findByUserIdAndThemeId(Long userId, Long themeId);

    int countByUser(User user);
}
