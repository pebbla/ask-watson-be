package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.cafe.CafeJpaRepository;
import com.apebble.askwatson.comm.exception.*;
import com.apebble.askwatson.escapecomplete.EscapeComplete;
import com.apebble.askwatson.escapecomplete.EscapeCompleteJpaRepository;
import com.apebble.askwatson.heart.Heart;
import com.apebble.askwatson.heart.HeartJpaRepository;
import com.apebble.askwatson.theme.category.Category;
import com.apebble.askwatson.theme.category.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final HeartJpaRepository heartJpaRepository;
    private final EscapeCompleteJpaRepository escapeCompleteJpaRepository;

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
    public List<ThemeDto.Response> getThemeList(String searchWord, Boolean sortByUpdateYn) {
        List<Theme> themeList = (searchWord == null)
                ? themeJpaRepository.findAll()
                : themeJpaRepository.findThemesBySearchWord(searchWord);

        if(sortByUpdateYn!=null && sortByUpdateYn) {
            themeList = sortByUpdate(themeList);
        }

        return convertToThemeDtoList(themeList);
    }

    private List<Theme> sortByUpdate(List<Theme> themeList) {
        List<Theme> nullModifiedAtList = new ArrayList<>();
        List<Theme> nullColumnList = new ArrayList<>();
        List<Theme> nonNullColumnList = new ArrayList<>();

        themeList.forEach(theme -> {
                    if(theme.getModifiedAt() == null)
                        nullModifiedAtList.add(theme);
                    else if(theme.getThemeName() == null || theme.getThemeName().equals("") ||
                            theme.getThemeExplanation() == null || theme.getThemeExplanation().equals("") ||
                            theme.getTimeLimit() == null || theme.getTimeLimit().equals(0) ||
                            theme.getMinNumPeople() == null || theme.getMinNumPeople().equals(0) ||
                            theme.getPrice() == null || theme.getPrice().equals(0) ||
                            theme.getImageUrl() == null || theme.getImageUrl().equals("") ||
                            theme.getReservationUrl() == null || theme.getReservationUrl().equals("") ||
                            theme.getCategory() == null)
                        nullColumnList.add(theme);
                    else nonNullColumnList.add(theme);
                });

        List<Theme> result = new ArrayList<>();
        result.addAll(nullModifiedAtList);
        result.addAll(nullColumnList);
        result.addAll(nonNullColumnList);

        return result;
    }

    // 테마 단건 조회
    public OneThemeDto.Response getOneTheme(Long themeId, Long userId) {
        Theme theme =  themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        return convertToOneThemeDto(theme, userId);
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

    public OneThemeDto.Response convertToOneThemeDto(Theme theme, Long userId){
        return new OneThemeDto.Response(
                theme,
                checkUserHeartedTheme(userId, theme.getId()),
                checkUserCompletedTheme(userId, theme.getId()));
    }

    private boolean checkUserHeartedTheme(Long userId, Long themeId) {
        Heart heart = heartJpaRepository.findByUserIdAndThemeId(userId, themeId);
        return heart != null;
    }

    private boolean checkUserCompletedTheme(Long userId, Long themeId) {
        EscapeComplete escapeComplete = escapeCompleteJpaRepository.findByUserIdAndThemeId(userId, themeId);
        return escapeComplete != null;
    }
}
