package com.apebble.askwatson.heart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apebble.askwatson.comm.exception.HeartNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeJpaRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserJpaRepository;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserJpaRepository userJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;


    /**
     * 좋아요 등록
     */
    public Long createHeart(Long userId, Long themeId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        theme.incHeartCount();
        return heartRepository.save(Heart.create(user, theme)).getId();
    }


    /**
     * 좋아요 해제
     */
    public void deleteHeart(Long heartId) {
        Heart heart = heartRepository.findById(heartId).orElseThrow(HeartNotFoundException::new);
        heart.getTheme().decHeartCount();
        heartRepository.delete(heart);
    }


    /**
     * 좋아요 단건 조회
     */
    @Transactional(readOnly = true)
    public Heart getOneHeartWithTheme(Long heartId){
        return heartRepository.findByIdWithTheme(heartId).orElseThrow(HeartNotFoundException::new);
    }


    /**
     * 좋아요 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Heart> getHeartsByUserId(Long userId){
        return heartRepository.findByUserIdWithCategory(userId);
    }

}
