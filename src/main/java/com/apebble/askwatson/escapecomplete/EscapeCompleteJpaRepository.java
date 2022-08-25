package com.apebble.askwatson.escapecomplete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EscapeCompleteJpaRepository extends JpaRepository<EscapeComplete, Long> {
    List<EscapeComplete> findByUserId(Long userId);


    @Query ("select e from EscapeComplete e where e.user.id = :userId and e.theme.id = :themeId")
    EscapeComplete findByUserIdAndThemeId(@Param("userId") Long userId, @Param("themeId") Long themeId);

}
