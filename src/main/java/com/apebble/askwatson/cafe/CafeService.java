package com.apebble.askwatson.cafe;


import com.apebble.askwatson.cafe.company.Company;
import com.apebble.askwatson.cafe.company.CompanyJpaRepository;
import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.cafe.location.LocationJpaRepository;
import com.apebble.askwatson.comm.exception.CafeNotFoundException;
import com.apebble.askwatson.comm.exception.CompanyNotFoundException;
import com.apebble.askwatson.comm.exception.LocationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
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

        // WKTReader를 통해 WKT를 실제 타입으로 변환
        String pointWKT = String.format("POINT(%s %s)", params.getLongitude(), params.getLatitude());
        Point point = (Point) new WKTReader().read(pointWKT);

        Cafe cafe = Cafe.builder()
                .cafeName(params.getCafeName())
                .cafePhoneNum(params.getCafePhoneNum())
                .company(company)
                .location(location)
                .geography(point)
                .build();

        return convertToCafeDto(cafeJpaRepository.save(cafe));
    }

    // 방탈출 카페 전체 조회
    public List<CafeDto.Response> getCafes(Long locationId, Long companyId) {
        Location location = null;
        Company company = null;

        if(locationId != null)
            location = locationJpaRepository.findById(locationId).orElseThrow(LocationNotFoundException::new);
        if(companyId != null)
            company = companyJpaRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);

        return convertToCafeDtoList(cafeJpaRepository.findCafesByLocationAndCompany(location, company));
    }

    // 방탈출 카페 단건 조회
    public CafeDto.Response getOneCafe(Long cafeId) {
        return convertToCafeDto(cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new));
    }

    // 방탈출 카페 수정
    public CafeDto.Response modifyCafe(Long cafeId, CafeParams params) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Company company = companyJpaRepository.findById(params.getCompanyId()).orElseThrow(CompanyNotFoundException::new);

        cafe.setCafeName(params.getCafeName());
        cafe.setCafePhoneNum(params.getCafePhoneNum());
        cafe.setCompany(company);
        cafe.setLocation(location);

        return convertToCafeDto(cafe);
    }

    // 방탈출 카페 삭제
    public void deleteCafe(Long cafeId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        cafeJpaRepository.delete(cafe);
    }

    public List<CafeDto.Response> convertToCafeDtoList(List<Cafe> cafeList){
        return cafeList.stream().map(CafeDto.Response::new).collect(toList());
    }

    public CafeDto.Response convertToCafeDto(Cafe cafe){
        return new CafeDto.Response(cafe);
    }
}
