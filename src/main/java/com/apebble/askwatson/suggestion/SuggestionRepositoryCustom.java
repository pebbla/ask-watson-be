package com.apebble.askwatson.suggestion;

import java.util.Optional;

public interface SuggestionRepositoryCustom {

    Optional<Suggestion> findByIdWithCafeAndUser(Long id);

}
