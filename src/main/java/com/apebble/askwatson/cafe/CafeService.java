package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.company.Company;
import com.apebble.askwatson.cafe.company.CompanyJpaRepository;
import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.cafe.location.LocationJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.CompanyNotFoundException;
import com.apebble.askwatson.comm.exception.DataIntegrityViolationException;
import com.apebble.askwatson.comm.exception.LocationNotFoundException;
import com.apebble.askwatson.comm.util.GeographyConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.io.ParseException;
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
public class CafeService {
    private final CafeJpaRepository cafeJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;

    // 방탈출 카페 등록
    public CafeDto.Response createCafe(CafeParams params) throws ParseException {
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Company company = companyJpaRepository.findById(params.getCompanyId()).orElseThrow(CompanyNotFoundException::new);

        Cafe cafe = Cafe.builder()
                .cafeName(params.getCafeName())
                .cafePhoneNum(params.getCafePhoneNum())
                .company(company)
                .website(params.getWebsite())
                .address(params.getAddress())
                .location(location)
                .geography(GeographyConverter.strToPoint(params.getLongitude(), params.getLatitude()))
                .isEnglishPossible(params.getIsEnglishPossible())
                .build();

        return convertToCafeDto(cafeJpaRepository.save(cafe));
    }

    // 방탈출 카페 전체 조회
    public Page<CafeDto.Response> getCafes(CafeSearchOptions searchOptions, Pageable pageable) {
        Page<Cafe> cafeList;
        cafeList = (searchOptions == null)
                ? cafeJpaRepository.findAll(pageable)
                : cafeJpaRepository.findCafesByOptions(searchOptions, pageable);

        return convertToCafeDtoPage(cafeList);
    }

    // 방탈출 카페 전체 조회(리스트 - 관리자웹 개발용)
    public List<CafeDto.Response> getCafeList() {
        return convertToCafeDtoList(cafeJpaRepository.findAll());
    }

    // 방탈출 카페 단건 조회
    public CafeDto.Response getOneCafe(Long cafeId) {
        return convertToCafeDto(cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new));
    }

    // 방탈출 카페 수정
    public CafeDto.Response modifyCafe(Long cafeId, CafeParams params) throws ParseException{
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Company company = companyJpaRepository.findById(params.getCompanyId()).orElseThrow(CompanyNotFoundException::new);

        cafe.update(params, location, company);

        return convertToCafeDto(cafe);
    }

    // 방탈출 카페 삭제
    public void deleteCafe(Long cafeId) throws DataIntegrityViolationException {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        cafeJpaRepository.delete(cafe);
    }

    public Page<CafeDto.Response> convertToCafeDtoPage(Page<Cafe> cafeList){
        return cafeList.map(CafeDto.Response::new);
    }

    public List<CafeDto.Response> convertToCafeDtoList(List<Cafe> cafeList){
        return cafeList.stream().map(CafeDto.Response::new).collect(toList());
    }

    public CafeDto.Response convertToCafeDto(Cafe cafe){
        return new CafeDto.Response(cafe);
    }
}
