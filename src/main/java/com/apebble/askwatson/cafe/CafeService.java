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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {
    private final CafeJpaRepository cafeJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;

    // 방탈출 카페 등록
    public Cafe createCafe(CafeParams params) {
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Company company = companyJpaRepository.findById(params.getCompanyId()).orElseThrow(CompanyNotFoundException::new);

        Cafe cafe = Cafe.builder()
                .cafeName(params.getCafeName())
                .cafePhoneNum(params.getCafePhoneNum())
                .company(company)
                .location(location)
                .build();

        return cafeJpaRepository.save(cafe);
    }

    // 방탈출 카페 전체 조회
    public List<Cafe> getCafes() {
        return cafeJpaRepository.findAll();
    }

    // 방탈출 카페 단건 조회
    public Cafe getOneCafe(Long cafeId) {
        return cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
    }

    // 방탈출 카페 수정
    public Cafe modifyCafe(Long cafeId, CafeParams params) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        Location location = locationJpaRepository.findById(params.getLocationId()).orElseThrow(LocationNotFoundException::new);
        Company company = companyJpaRepository.findById(params.getCompanyId()).orElseThrow(CompanyNotFoundException::new);

        cafe.setCafeName(params.getCafeName());
        cafe.setCafePhoneNum(params.getCafePhoneNum());
        cafe.setCompany(company);
        cafe.setLocation(location);

        return cafe;
    }

    // 방탈출 카페 삭제
    public void deleteCafe(Long cafeId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        cafeJpaRepository.delete(cafe);
    }
}
