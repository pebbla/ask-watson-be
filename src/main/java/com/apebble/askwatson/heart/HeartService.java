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

import static java.util.stream.Collectors.toList;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {

    private final HeartJpaRepository heartJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;
    
    // 좋아요 등록
    public Long createHeart(Long userId, Long themeId) {
        User user = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        Heart heart = Heart.builder()
                .user(user)
                .theme(theme)
                .build();

        theme.incHeartCount();

        return heartJpaRepository.save(heart).getId();
    }

    // 좋아요 해제
    public void deleteHeart(Long heartId) {
        Heart heart = heartJpaRepository.findById(heartId).orElseThrow(HeartNotFoundException::new);
        heart.getTheme().decHeartCount();
        heartJpaRepository.delete(heart);
    }

    // 좋아요 목록 조회
    @Transactional(readOnly = true)
    public List<HeartDto.Response> getHeartsByUserId(Long userId){
        List<Heart> heartList = heartJpaRepository.findByUserId(userId);
        return convertToHeartDtoList(heartList);
    }

    private List<HeartDto.Response> convertToHeartDtoList(List<Heart> heartList){
        return heartList.stream().map(HeartDto.Response::new).collect(toList());
    }

}
