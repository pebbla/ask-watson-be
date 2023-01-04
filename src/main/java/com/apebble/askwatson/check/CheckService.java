package com.apebble.askwatson.check;

import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.review.ReviewRepository;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CheckService {

    private final UserJpaRepository userJpaRepository;
    private final ThemeRepository themeRepository;
    private final CheckRepository checkRepository;
    private final ReviewRepository reviewRepository;


    /**
     * 탈출 완료 등록
     */
    public Long createCheck(Long userId, Long themeId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        theme.incEscapeCount();
        return checkRepository.save(Check.create(user, theme)).getId();
    }


    /**
     * 탈출 완료 단건 조회
     */
    @Transactional(readOnly = true)
    public Check findOneWithTheme(Long checkId) {
        return checkRepository.findById(checkId).orElseThrow(CheckNotFoundException::new);
    }


    /**
     * 탈출 완료 일시 수정
     */
    public void modifyCheckDt(Long checkId, LocalDate newEcDt) {
        Check check = checkRepository.findById(checkId).orElseThrow(CheckNotFoundException::new);
        check.update(newEcDt);
    }


    /**
     * 탈출 완료 취소(리뷰 여부 확인)
     */
    public void deleteCheckIfNoReview(Long checkId) {
        Check check = checkRepository.findById(checkId).orElseThrow(CheckNotFoundException::new);

        if(!doesReviewExists(check))
            deleteCheck(check);
    }

    private boolean doesReviewExists(Check check) {
        Optional<Review> review = reviewRepository.findByUserAndTheme(check.getUser(), check.getTheme());

        if(review.isPresent())
            throw new CheckUndeletableException();

        return false;
    }

    public void deleteCheck(Check check) {
        check.getTheme().decEscapeCount();
        checkRepository.delete(check);
    }

}
