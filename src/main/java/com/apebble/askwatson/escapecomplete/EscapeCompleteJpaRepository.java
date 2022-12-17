package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EscapeCompleteJpaRepository extends JpaRepository<EscapeComplete, Long> {
    List<EscapeComplete> findByUserId(Long userId);

    Optional<EscapeComplete> findByUserIdAndThemeId(Long userId, Long themeId);

    int countByUser(User user);

    @Query(value = "select e from EscapeComplete e join fetch e.theme where e.id=:id")
    Optional<EscapeComplete> findByIdWithTheme(Long id);

}
