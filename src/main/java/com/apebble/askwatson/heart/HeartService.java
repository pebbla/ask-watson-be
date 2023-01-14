package com.apebble.askwatson.heart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apebble.askwatson.comm.exception.HeartNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.comm.exception.UserNotFoundException;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeRepository;
import com.apebble.askwatson.user.User;
import com.apebble.askwatson.user.UserRepository;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;


    /**
     * 좋아요 등록
     */
    public Long createHeart(Long userId, Long themeId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
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

}
