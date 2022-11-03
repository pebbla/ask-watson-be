package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.review.ReviewJpaRepository;
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
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EscapeCompleteService {

    private final UserJpaRepository userJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;
    private final EscapeCompleteJpaRepository escapeCompleteJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;


    /**
     * 탈출 완료 등록(DTO)
     */
    public EscapeCompleteDto.Response createEscapeCompleteWithDto(Long userId, Long themeId) {
        return convertToDto(createEscapeComplete(userId, themeId));
    }

    // 탈출 완료 등록
    public EscapeComplete createEscapeComplete(Long userId, Long themeId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);

        EscapeComplete escapeComplete = EscapeComplete.builder()
                .user(user)
                .theme(theme)
                .build();

        theme.incEscapeCount();
        return escapeCompleteJpaRepository.save(escapeComplete);
    }


    /**
     * 사용자별 탈출완료 리스트 조회
     */
    @Transactional(readOnly = true)
    public List<EscapeCompleteDto.Response> getEscapeCompletesByUserId(Long userId) {
        return convertToDtoList(escapeCompleteJpaRepository.findByUserId(userId));
    }


    /**
     * 탈출 완료 일시 수정
     */
    public EscapeCompleteDto.Response modifyEscapeCompleteDt(Long escapeCompleteId, LocalDate newEcDt) {
        EscapeComplete escapeComplete = escapeCompleteJpaRepository.findById(escapeCompleteId).orElseThrow(EscapeCompleteNotFoundException::new);
        escapeComplete.update(newEcDt);

        return convertToDto(escapeComplete);
    }


    /**
     * 탈출 완료 취소(리뷰 여부 확인)
     */
    public void deleteEscapeCompleteByCheckingReview(Long escapeCompleteId) {
        EscapeComplete escapeComplete = escapeCompleteJpaRepository.findById(escapeCompleteId).orElseThrow(EscapeCompleteNotFoundException::new);

        if(!doesReviewExists(escapeComplete))
            deleteEscapeComplete(escapeComplete);
    }

    private boolean doesReviewExists(EscapeComplete escapeComplete) {
        Optional<Review> review = reviewJpaRepository.findByUserAndTheme(escapeComplete.getUser(), escapeComplete.getTheme());

        if(review.isPresent())
            throw new EscapeCompleteUndeletableException();

        return false;
    }

    public void deleteEscapeComplete(EscapeComplete escapeComplete) {
        escapeComplete.getTheme().decEscapeCount();
        escapeCompleteJpaRepository.delete(escapeComplete);
    }


    //==DTO 변환 함수==//
    private List<EscapeCompleteDto.Response> convertToDtoList(List<EscapeComplete> escapeCompleteList){
        return escapeCompleteList.stream().map(EscapeCompleteDto.Response::new).collect(toList());
    }

    private EscapeCompleteDto.Response convertToDto(EscapeComplete escapeComplete){
        return new EscapeCompleteDto.Response(escapeComplete);
    }

}
