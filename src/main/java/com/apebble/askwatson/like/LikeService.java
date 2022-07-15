package com.apebble.askwatson.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apebble.askwatson.comm.exception.LikeNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.theme.ThemeJpaRepository;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeJpaRepository likeJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;

    
    // 좋아요 등록
    public Likes createLike(Long userId, Long themeId) {
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        Likes likes = Likes.builder()
                .userId(userId)
                .theme(theme)
                .build();
        theme.setLikeCount(theme.getLikeCount() + 1);
        return likeJpaRepository.save(likes);
    }


    // 좋아요 해제
    public void deleteLike(Long themeId, Long likesId) {
        Likes likes = likeJpaRepository.findById(likesId).orElseThrow(LikeNotFoundException::new);
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        theme.setLikeCount(theme.getLikeCount() - 1);
        likeJpaRepository.delete(likes);
    }


    // 좋아요 목록 조회
    //TODO : ThemeDto 가 뭔지 조금 더 고민하기
    public List<Likes> getLikeThemeList(Long userId){
        List<Likes> likeList = likeJpaRepository.findByUserId(userId);
        return likeList;
    }
    
}
