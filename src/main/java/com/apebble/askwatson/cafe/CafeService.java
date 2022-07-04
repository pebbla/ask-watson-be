package com.apebble.askwatson.cafe;


import com.apebble.askwatson.comm.exception.CafeNotFoundException;
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

    // 방탈출 카페 등록
    public Cafe createCafe(CafeParams params) {
        Cafe cafe = Cafe.builder()
                .cafeName(params.getCafeName())
                .cafePhoneNum(params.getCafePhoneNum())
                .company(params.getCompany())
                .locationSort(params.getLocationSort())
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

        cafe.setCafeName(params.getCafeName());
        cafe.setCafePhoneNum(params.getCafePhoneNum());
        cafe.setCompany(params.getCompany());
        cafe.setLocationSort(params.getLocationSort());

        return cafe;
    }

    // 방탈출 카페 삭제
    public void deleteCafe(Long cafeId) {
        Cafe cafe = cafeJpaRepository.findById(cafeId).orElseThrow(CafeNotFoundException::new);
        cafeJpaRepository.delete(cafe);
    }
}
