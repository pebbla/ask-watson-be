package com.apebble.askwatson.suggestion;

import com.apebble.askwatson.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long>, SuggestionRepositoryCustom {

    List<Suggestion> findByUser(User user);

}
