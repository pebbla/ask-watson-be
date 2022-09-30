package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.cafe.location.LocationJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.LocationNotFoundException;
import com.apebble.askwatson.comm.util.GeographyConverter;
import com.apebble.askwatson.comm.util.StringConverter;
import com.apebble.askwatson.config.GoogleCloudConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;

import static java.util.stream.Collectors.toList;



@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {

    private final CafeJpaRepository cafeJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final GoogleCloudConfig googleCloudConfig;
    private final EntityManager entityManager;


    // 방탈출 카페 등록
    public CafeDto.Response createCafe(CafeParams params, MultipartFile file) throws Exception {
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);

        Cafe cafe = Cafe.builder()
            .cafeName(params.getCafeName())
            .cafePhoneNum(params.getCafePhoneNum())
            .website(params.getWebsite())
            .address(params.getAddress())
            .location(location)
            .geography(GeographyConverter.strToPoint(params.getLongitude(), params.getLatitude()))
            .isEnglishPossible(params.getIsEnglishPossible())
            .build();
        entityManager.persist(cafe);

        String imageUrl = googleCloudConfig.uploadObject("cafe/" + cafe.getId() + "_" + cafe.getCafeName().replace(" ", ""), file);
        cafe.setImageUrl(imageUrl);

        return convertToCafeDto(cafeJpaRepository.save(cafe));
    }

    // 방탈출 카페 전체 조회
    public Page<CafeDto.Response> getCafes(CafeSearchOptions searchOptions, Pageable pageable) {
        Page<Cafe> cafeList;
        cafeList = (searchOptions == null)
                ? cafeJpaRepository.findCafesByIsAvailable(true, pageable)
                : cafeJpaRepository.findCafesByOptionsAndIsAvailable(searchOptions, true, pageable);

        return convertToCafeDtoPage(cafeList);
    }

    // 방탈출 카페 전체 조회(리스트 - 관리자웹 개발용)
    public List<CafeDto.Response> getCafeList(String searchWord, Boolean sortByUpdateYn) {
       List<Cafe> cafeList = (searchWord == null)
                ? cafeJpaRepository.findAll()
                : cafeJpaRepository.findCafesBySearchWord(searchWord);

       if(sortByUpdateYn!=null && sortByUpdateYn) {
           cafeList = sortByUpdate(cafeList);
       }

        return convertToCafeDtoList(cafeList);
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

    // 방탈출 카페 단건 조회
    public CafeDto.Response getOneCafe(Long cafeId) {
        return convertToCafeDto(cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new));
    }


    // 방탈출 카페 수정
    public CafeDto.Response modifyCafe(Long cafeId, CafeParams params, @Nullable MultipartFile file) throws Exception {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        String imageUrl = params.getImageUrl();

        if(file != null) {
            googleCloudConfig.deleteObject("cafe/" + cafe.getId() + "_");
            imageUrl = googleCloudConfig.uploadObject("cafe/" + cafe.getId() + "_" + params.getCafeName().replace(" ", ""), file);
            params.setImageUrl(imageUrl);
        }

        cafe.update(params, location);
        return convertToCafeDto(cafe);
    }

    // 방탈출 카페 삭제
    public void deleteUselessCafeInfo(Long cafeId) {
        googleCloudConfig.deleteObject("cafe/" + cafeId + "_");
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        setThemesUnavailable(cafe);
        cafe.deleteUselessInfo();
    }

    private void setThemesUnavailable(Cafe cafe) {
        cafe.getThemeList().forEach(theme -> theme.setAvailable(false));
    }

    private Page<CafeDto.Response> convertToCafeDtoPage(Page<Cafe> cafeList){
        return cafeList.map(CafeDto.Response::new);
    }

    private List<CafeDto.Response> convertToCafeDtoList(List<Cafe> cafeList){
        return cafeList.stream().map(CafeDto.Response::new).collect(toList());
    }

    private CafeDto.Response convertToCafeDto(Cafe cafe){
        return new CafeDto.Response(cafe);
    }
}



