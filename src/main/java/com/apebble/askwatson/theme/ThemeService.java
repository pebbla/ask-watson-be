package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.cafe.CafeRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.CategoryNotFoundException;
import com.apebble.askwatson.comm.exception.ThemeNotFoundException;
import com.apebble.askwatson.config.GoogleCloudConfig;
import com.apebble.askwatson.theme.category.Category;
import com.apebble.askwatson.theme.category.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.annotation.Nullable;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ThemeService {

    private final CafeRepository cafeRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final ThemeRepository themeRepository;
    private final GoogleCloudConfig googleCloudConfig;

    
    /**
     * 방탈출 테마 등록
     */
    public Long createTheme(Long cafeId, ThemeDto.Request params, MultipartFile file) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        Category category = categoryJpaRepository.findById(params.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        Theme savedTheme = themeRepository.save(Theme.create(cafe, category, params));
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
    public List<Theme> getThemesByCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        return themeRepository.findThemesByCafe(cafe);
    }


    /**
     * 테마 단건 조회
     */
    @Transactional(readOnly = true)
    public Theme findOne(Long themeId) {
        return themeRepository.findByIdWithCategory(themeId).orElseThrow(ThemeNotFoundException::new);
    }


    /**
     * 테마 정보 수정
     */
    public void modifyTheme(Long themeId, ThemeDto.Request params, @Nullable MultipartFile file) {
        Theme theme = themeRepository.findByIdWithCategory(themeId).orElseThrow(ThemeNotFoundException::new);
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
        Theme theme = themeRepository.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        theme.updateAvailability(isAvailable);
    }

}
