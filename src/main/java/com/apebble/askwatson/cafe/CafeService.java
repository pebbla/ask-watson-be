package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.cafe.location.LocationJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.LocationNotFoundException;
import org.locationtech.jts.geom.Point;
import com.apebble.askwatson.comm.util.GeographyConverter;
import com.apebble.askwatson.config.GoogleCloudConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.locationtech.jts.io.ParseException;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {

    private final CafeJpaRepository cafeJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final GoogleCloudConfig googleCloudConfig;


    /**
     * 방탈출 카페 등록
     */
    public Long createCafe(CafeDto.Request params, MultipartFile file) throws ParseException {
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Point geography = GeographyConverter.strToPoint(params.getLongitude(), params.getLatitude());

        Cafe savedCafe = cafeJpaRepository.save(Cafe.create(params, location, geography));
        if(file != null) savedCafe.updateImageUrl(addToGoogleStorage(savedCafe.getId(), file));

        return savedCafe.getId();
    }

    private String addToGoogleStorage(Long cafeId, MultipartFile file) {
        String gcsPath = "cafe/" + cafeId + "_cafe";
        String imageUrl = googleCloudConfig.uploadObject(gcsPath, file);
        return imageUrl;
    }


    /**
     * 방탈출 카페 전체 조회
     */
    @Transactional(readOnly = true)
    public Page<Cafe> getCafes(CafeSearchOptions searchOptions, Pageable pageable) {
        Page<Cafe> cafeList;
        cafeList = (searchOptions == null)
                ? cafeJpaRepository.findCafesByIsAvailable(true, pageable)
                : cafeJpaRepository.findCafesByOptionsAndIsAvailable(searchOptions, true, pageable);

        return cafeList;
    }


    /**
     * 방탈출 카페 전체 조회(리스트 - 관리자웹 개발용)
     */
    @Transactional(readOnly = true)
    public List<Cafe> getCafeList(String searchWord, Boolean sortByUpdateYn) {
       List<Cafe> cafeList = (searchWord == null)
                ? cafeJpaRepository.findAllCafes()
                : cafeJpaRepository.findCafesBySearchWord(searchWord);

       if(sortByUpdateYn!=null && sortByUpdateYn) {
           cafeList = sortByUpdate(cafeList);
       }

        return cafeList;
    }

    /**
     * 방탈출 카페 단건 조회
     */
    @Transactional(readOnly = true)
    public Cafe findOne(Long cafeId) {
        return cafeJpaRepository.findByIdWithLocation(cafeId).orElseThrow(CafeNotFoundException::new);
    }

    private List<Cafe> sortByUpdate(List<Cafe> cafeList) {
        List<Cafe> nullModifiedAtList = new ArrayList<>();
        List<Cafe> nullColumnList = new ArrayList<>();
        List<Cafe> nonNullColumnList = new ArrayList<>();

        cafeList.forEach(cafe -> {
            if(cafe.getModifiedAt() == null)
                nullModifiedAtList.add(cafe);
            else if(cafe.getCafeName() == null || cafe.getCafeName().equals("") ||
                    cafe.getCafePhoneNum() == null || cafe.getCafePhoneNum().equals("") ||
                    cafe.getWebsite() == null || cafe.getWebsite().equals("") ||
                    cafe.getAddress() == null || cafe.getAddress().equals("") ||
                    cafe.getImageUrl() == null || cafe.getImageUrl().equals("") ||
                    cafe.getLocation() == null)
                nullColumnList.add(cafe);
            else nonNullColumnList.add(cafe);
        });

        List<Cafe> result = new ArrayList<>();
        result.addAll(nullModifiedAtList);
        result.addAll(nullColumnList);
        result.addAll(nonNullColumnList);

        return result;
    }


    /**
     * 방탈출 카페 단건 조회
     */
    @Transactional(readOnly = true)
    public Cafe getOneCafe(Long cafeId) {
        return cafeJpaRepository.findByIdWithLocation(cafeId).orElseThrow(CafeNotFoundException::new);
    }


    /**
     * 방탈출 카페 수정
     */
    public void modifyCafe(Long cafeId, CafeDto.Request params, @Nullable MultipartFile file) throws ParseException {
        Cafe cafe = cafeJpaRepository.findByIdWithLocation(cafeId).orElseThrow(CafeNotFoundException::new);
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Point geography = GeographyConverter.strToPoint(params.getLongitude(), params.getLatitude());

        if(file != null) params.setImageUrl(updateGoogleStorage(cafeId, file));
        updateThemesAvailability(cafe, params.getIsAvailable());

        cafe.update(params, location, geography);
    }

    private String updateGoogleStorage(Long cafeId, MultipartFile file) {
        String gcsPath = "cafe/" + cafeId + "_cafe";
        googleCloudConfig.deleteObject(gcsPath);
        String imageUrl = googleCloudConfig.uploadObject(gcsPath, file);
        return imageUrl;
    }

    private void updateThemesAvailability(Cafe cafe, Boolean isCafeAvailable) {
        if(isCafeAvailable != null && !isCafeAvailable) {
            setThemesUnavailable(cafe);
        }
    }


    /**
     * 방탈출 카페 삭제
     */
    public void deleteUselessCafeInfo(Long cafeId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        setThemesUnavailable(cafe);
        cafe.deleteUselessInfo();
        googleCloudConfig.deleteObject("cafe/" + cafeId + "_cafe");
    }

    private void setThemesUnavailable(Cafe cafe) {
        cafe.getThemeList().forEach(theme -> theme.changeAvailability(false));
    }
}



