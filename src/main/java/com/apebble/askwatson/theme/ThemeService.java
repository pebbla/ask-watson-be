package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.cafe.CafeJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.CategoryNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.config.GoogleCloudConfig;
import com.apebble.askwatson.heart.Heart;
import com.apebble.askwatson.heart.HeartJpaRepository;
import com.apebble.askwatson.check.Check;
import com.apebble.askwatson.check.CheckJpaRepository;
import com.apebble.askwatson.theme.category.Category;
import com.apebble.askwatson.theme.category.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

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
    private final CheckJpaRepository checkJpaRepository;
    private final GoogleCloudConfig googleCloudConfig;

    
    /**
     * 방탈출 테마 등록
     */
    public Long createTheme(Long cafeId, ThemeDto.Request params, MultipartFile file) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        Category category = categoryJpaRepository.findById(params.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        Theme savedTheme = themeJpaRepository.save(Theme.create(cafe, category, params));
        if(file !=null) savedTheme.updateImageUrl(addToGoogleStorage(savedTheme.getId(), file));

        return savedTheme.getId();
    }

    private String addToGoogleStorage(Long themeId, MultipartFile file) {
        String gcsPath = "theme/" + themeId + "_theme";
        String imageUrl = googleCloudConfig.uploadObject(gcsPath, file);
        return imageUrl;
    }


    /**
     * 카페별 테마 조회
     */
    @Transactional(readOnly = true)
    public List<ThemeDto.Response> getThemesByCafe(Long cafeId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        return convertToThemeDtoList(themeJpaRepository.findThemesByCafe(cafe));
    }


    /**
     * 테마 목록 전체 조회
     */
    @Transactional(readOnly = true)
    public Page<ThemeDto.Response> getThemes(ThemeSearchOptions searchOptions, Pageable pageable) {
        Page<Theme> themeList;
        themeList = (searchOptions == null)
            ? themeJpaRepository.findThemesByIsAvailable(true, pageable)
            : themeJpaRepository.findThemesByOptionsAndIsAvailable(searchOptions, true, pageable);

        return convertToThemeDtoPage(themeList);
    }


    /**
     * 방탈출 테마 전체 조회(리스트 - 관리자웹용)
     */
    @Transactional(readOnly = true)
    public List<ThemeDto.Response> getThemeList(String searchWord, Boolean sortByUpdateYn) {
        List<Theme> themeList = (searchWord == null)
                ? themeJpaRepository.findAllThemes()
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


    /**
     * 테마 단건 조회(회원 하트 같이)
     */
    @Transactional(readOnly = true)
    public OneThemeDto.Response getOneThemeWithUserHearted(Long themeId, Long userId) {
        Theme theme = themeJpaRepository.findByIdWithCategory(themeId).orElseThrow(ThemeNotFoundException::new);
        return convertToOneThemeDto(theme, userId);
    }


    /**
     * 테마 단건 조회
     */
    @Transactional(readOnly = true)
    public Theme findOne(Long themeId) {
        return themeJpaRepository.findByIdWithCategory(themeId).orElseThrow(ThemeNotFoundException::new);
    }


    /**
     * 테마 정보 수정
     */
    public void modifyTheme(Long themeId, ThemeDto.Request params, @Nullable MultipartFile file) {
        Theme theme = themeJpaRepository.findByIdWithCategory(themeId).orElseThrow(ThemeNotFoundException::new);
        Category category = categoryJpaRepository.findById(params.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        if(file != null) params.setImageUrl(updateGoogleStorage(theme.getId(), file));
        theme.update(params, category);
    }

    private String updateGoogleStorage(Long themeId, MultipartFile file) {
        String gcsPath = "theme/" + themeId + "_theme";
        googleCloudConfig.deleteObject(gcsPath);
        String imageUrl = googleCloudConfig.uploadObject(gcsPath, file);
        return imageUrl;
    }


    /**
     * 테마 이용가능여부 변경
     */
    public void modifyThemeAvailability(Long themeId, Boolean isAvailable) {
        Theme theme = themeJpaRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        theme.changeAvailability(isAvailable);
    }

    private boolean checkUserHeartedTheme(Long userId, Long themeId) {
        if(userId == null) return false;

        Optional<Heart> heart = heartJpaRepository.findByUserIdAndThemeId(userId, themeId);
        return heart.isPresent();
    }

    private boolean checkUserCompletedTheme(Long userId, Long themeId) {
        if(userId == null) return false;

        Optional<Check> check = checkJpaRepository.findByUserIdAndThemeId(userId, themeId);
        return check.isPresent();
    }


    //==DTO 변환 메서드==//
    private Page<ThemeDto.Response> convertToThemeDtoPage(Page<Theme> themeList){
        return themeList.map(ThemeDto.Response::new);
    }

    private List<ThemeDto.Response> convertToThemeDtoList(List<Theme> themeList){
        return themeList.stream().map(ThemeDto.Response::new).collect(toList());
    }

    private OneThemeDto.Response convertToOneThemeDto(Theme theme, Long userId){
        return new OneThemeDto.Response(
                theme,
                checkUserHeartedTheme(userId, theme.getId()),
                checkUserCompletedTheme(userId, theme.getId()));
    }

}
