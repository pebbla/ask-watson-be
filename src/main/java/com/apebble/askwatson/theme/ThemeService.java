package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.cafe.CafeJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ThemeService {
    private final CafeJpaRepository cafeJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;

    // 방탈출 테마 등록
    public Theme createTheme(Long cafeId, ThemeParams params) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);

        Theme theme = Theme.builder()
                .cafe(cafe)
                .themeName(params.getThemeName())
                .themeExplanation(params.getThemeExplanation())
                .timeLimit(params.getTimeLimit())
                .difficulty(params.getDifficulty())
                .category(params.getCategory())
                .build();

        return themeJpaRepository.save(theme);
    }

    // 카페별 테마 조회
    public List<Theme> getThemesByCafe(Long cafeId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        return themeJpaRepository.findThemesByCafe(cafe);
    }

    // 테마 목록 전체 조회
    public List<Theme> getAllThemes() {
        return themeJpaRepository.findAll();
    }

    // 테마 단건 조회
    public Theme getOneTheme(Long themeId) {
        return themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
    }

    // 테마 수정
    public Theme modifyTheme(Long themeId, ThemeParams params) {
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);

        theme.setThemeName(params.getThemeName());
        theme.setCategory(params.getCategory());
        theme.setThemeExplanation(params.getThemeExplanation());
        theme.setDifficulty(params.getDifficulty());
        theme.setTimeLimit(params.getTimeLimit());

        return theme;
    }

    // 테마 삭제
    public void deleteTheme(Long themeId) {
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        themeJpaRepository.delete(theme);
    }
}
