package com.apebble.askwatson.check;

import com.apebble.askwatson.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckJpaRepository extends JpaRepository<Check, Long> {

    @Query("select c from checks c join fetch c.theme t join fetch t.category ct where c.user.id=:userId")
    List<Check> findByUserId(@Param("userId") Long userId);

    Optional<Check> findByUserIdAndThemeId(Long userId, Long themeId);

    int countByUser(User user);

    @Query("select c from checks c join fetch c.theme t join fetch t.category ct where c.id=:id")
    Optional<Check> findByIdWithTheme(@Param("id") Long id);

}
