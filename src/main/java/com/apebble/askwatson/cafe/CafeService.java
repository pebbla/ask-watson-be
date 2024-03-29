package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.cafe.location.LocationJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.LocationNotFoundException;
import com.apebble.askwatson.theme.Theme;
import org.locationtech.jts.geom.Point;
import com.apebble.askwatson.comm.util.GeographyConverter;
import com.apebble.askwatson.config.GoogleCloudConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.annotation.Nullable;

import org.locationtech.jts.io.ParseException;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final GoogleCloudConfig googleCloudConfig;


    /**
     * 방탈출 카페 등록
     */
    public Long createCafe(CafeDto.Request params, MultipartFile file) throws ParseException {
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Point geography = GeographyConverter.strToPoint(params.getLongitude(), params.getLatitude());

        Cafe savedCafe = cafeRepository.save(Cafe.create(params, location, geography));
        if(file != null) savedCafe.updateImageUrl(addToGoogleStorage(savedCafe.getId(), file));

        return savedCafe.getId();
    }

    private String addToGoogleStorage(Long cafeId, MultipartFile file) {
        String gcsPath = "cafe/" + cafeId + "_cafe";
        String imageUrl = googleCloudConfig.uploadObject(gcsPath, file);
        return imageUrl;
    }


    /**
     * 방탈출 카페 단건 조회
     */
    @Transactional(readOnly = true)
    public Cafe findOne(Long cafeId) {
        return cafeRepository.findByIdWithLocation(cafeId).orElseThrow(CafeNotFoundException::new);
    }


    /**
     * 방탈출 카페 수정
     */
    public void modifyCafe(Long cafeId, CafeDto.Request params, @Nullable MultipartFile file) throws ParseException {
        Cafe cafe = cafeRepository.findByIdWithLocation(cafeId).orElseThrow(CafeNotFoundException::new);
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Point geography = GeographyConverter.strToPoint(params.getLongitude(), params.getLatitude());

        if(file != null) params.setImageUrl(updateGoogleStorage(cafeId, file));
        updateThemesAvailability(cafe.getThemeList(), params.getIsAvailable());

        cafe.update(params, location, geography);
    }

    private String updateGoogleStorage(Long cafeId, MultipartFile file) {
        String gcsPath = "cafe/" + cafeId + "_cafe";
        googleCloudConfig.deleteObject(gcsPath);
        String imageUrl = googleCloudConfig.uploadObject(gcsPath, file);
        return imageUrl;
    }


    /**
     * 방탈출 카페 이용가능여부 수정
     */
    public void modifyCafeAvailability(Long cafeId, boolean isAvailable) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        cafe.updateAvailability(isAvailable);
        updateThemesAvailability(cafe.getThemeList(), isAvailable);
    }

    private void updateThemesAvailability(List<Theme> themes, Boolean isCafeAvailable) {
        if(isCafeAvailable != null) {
            themes.forEach(theme -> theme.updateAvailability(isCafeAvailable));
        }
    }

}



