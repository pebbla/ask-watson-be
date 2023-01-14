package com.apebble.askwatson.suggestion;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.cafe.CafeRepository;
import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final CafeRepository cafeRepository;
    private final UserRepository userJpaRepository;


    /**
     * 건의 등록
     */
    public Long createSuggestion(Long cafeId, Long userId, SuggestionDto.Request params) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return suggestionRepository.save(Suggestion.create(params, cafe, user)).getId();
    }


    /**
     * 건의 단건 조회
     */
    public Suggestion getOneSuggestion(Long suggestionId) {
        return suggestionRepository.findByIdWithCafeAndUser(suggestionId).orElseThrow(SecurityException::new);
    }


    /**
     * 건의 처리 상태 변경
     */
    public void modifySuggestionHandledYn(Long suggestionId, Boolean handledYn) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId).orElseThrow(SuggestionNotFoundException::new);
        suggestion.updateHandledYn(handledYn);
    }


    /**
     * 건의 삭제
     */
    public void deleteSuggestion(Long suggestionId) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId).orElseThrow(SuggestionNotFoundException::new);
        suggestionRepository.delete(suggestion);
    }

}
