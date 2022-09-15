package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.cafe.CafeJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.CategoryNotFoundException;
import com.apebble.askwatson.comm.exception.DataIntegrityViolationException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.theme.category.Category;
import com.apebble.askwatson.theme.category.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ThemeService {
    private final CafeJpaRepository cafeJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ThemeJpaRepository themeJpaRepository;

    // 방탈출 테마 등록
    public Theme createTheme(Long cafeId, ThemeParams params) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        Category category = categoryJpaRepository.findById(params.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        Theme theme = Theme.builder()
                .cafe(cafe)
                .themeName(params.getThemeName())
                .themeExplanation(params.getThemeExplanation())
                .timeLimit(params.getTimeLimit())
                .category(category)
                .minNumPeople(params.getMinNumPeople())
                .price(params.getPrice())
                .reservationUrl(params.getReservationUrl())
                .imageUrl(params.getImageUrl())
                .build();

        return themeJpaRepository.save(theme);
    }

    // 카페별 테마 조회
    public List<Theme> getThemesByCafe(Long cafeId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        return themeJpaRepository.findThemesByCafe(cafe);
    }

    // 테마 목록 전체 조회
    public Page<ThemeDto.Response> getThemes(ThemeSearchOptions searchOptions, Pageable pageable) {
        Page<Theme> themeList;
        themeList = (searchOptions == null)
            ? themeJpaRepository.findAll(pageable)
            : themeJpaRepository.findThemesByOptions(searchOptions, pageable);

        return convertToThemeDtoPage(themeList);
    }

    // 방탈출 테마 전체 조회(리스트 - 관리자웹용)
    public List<ThemeDto.Response> getThemeList(String searchWord) {
        List<Theme> themeList = (searchWord == null)
                ? themeJpaRepository.findAll()
                : themeJpaRepository.findThemesBySearchWord(searchWord);

        return convertToThemeDtoList(themeList);
    }

    // 테마 단건 조회
    public ThemeDto.Response getOneTheme(Long themeId) {
        return convertToThemeDto(themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new));
    }

    // 테마 수정
    public ThemeDto.Response modifyTheme(Long themeId, ThemeParams params) {
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        Category category = categoryJpaRepository.findById(params.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        theme.update(params, category);

        return convertToThemeDto(theme);
    }

    // 테마 삭제
    public void deleteTheme(Long themeId) throws DataIntegrityViolationException {
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        themeJpaRepository.delete(theme);
    }

    public Page<ThemeDto.Response> convertToThemeDtoPage(Page<Theme> themeList){
        return themeList.map(ThemeDto.Response::new);
    }

    public List<ThemeDto.Response> convertToThemeDtoList(List<Theme> themeList){
        return themeList.stream().map(ThemeDto.Response::new).collect(toList());
    }

    public ThemeDto.Response convertToThemeDto(Theme theme){
        return new ThemeDto.Response(theme);
    }
}
