package com.apebble.askwatson.cafe;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 방탈출 카페 단건 조회

    // 방탈출 카페 수정

    // 방탈출 카페 삭제
}
