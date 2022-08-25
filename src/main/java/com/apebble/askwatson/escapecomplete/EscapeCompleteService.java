package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeJpaRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EscapeCompleteService {
    private final UserJpaRepository userJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;
    private final EscapeCompleteJpaRepository escapeCompleteJpaRepository;

    // 탈출 완료 등록
    public EscapeComplete createEscapeComplete(Long userId, Long themeId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);

        EscapeComplete escapeComplete = EscapeComplete.builder()
                .user(user)
                .theme(theme)
                .build();

        theme.setEscapeCount(theme.getEscapeCount()+1);

        return escapeCompleteJpaRepository.save(escapeComplete);
    }

    // 사용자별 탈출완료 리스트 조회
    public List<EscapeComplete> getEscapeCompletesByUserId(Long userId) {
        return escapeCompleteJpaRepository.findByUserId(userId);
    }

    // 탈출 완료 일시 수정
    public EscapeComplete modifyEscapeCompleteDt(Long escapeCompleteId, LocalDate newEcDt) {
        EscapeComplete escapeComplete = escapeCompleteJpaRepository.findById(escapeCompleteId).orElseThrow(EscapeCompleteNotFoundException::new);
        escapeComplete.update(newEcDt);

        return escapeComplete;
    }

    // 탈출 완료 취소
    public void deleteEscapeComplete(Long escapeCompleteId) {
        EscapeComplete escapeComplete = escapeCompleteJpaRepository.findById(escapeCompleteId).orElseThrow(EscapeCompleteNotFoundException::new);
        Theme theme = escapeComplete.getTheme();
        theme.setEscapeCount(theme.getEscapeCount()-1);
        escapeCompleteJpaRepository.delete(escapeComplete);
    }
}
