package com.apebble.askwatson.cafe.location;

import com.apebble.askwatson.comm.exception.DataIntegrityViolationException;
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
public class LocationService {

    private final LocationJpaRepository locationJpaRepository;

    // 위치 등록
    public Long createLocation(LocationParams params) {
        return locationJpaRepository.save(Location.create(params)).getId();
    }

    // 위치 전체 조회
    @Transactional(readOnly = true)
    public List<Location> getLocations() {
        return locationJpaRepository.findAll();
    }

    // 위치 수정
    public void modifyLocation(Long locationId, LocationParams params) {
        Location location =locationJpaRepository.findById(locationId).orElseThrow(LocationNotFoundException::new);
        location.update(params);
    }

    // 위치 삭제
    public void deleteLocation(Long locationId) throws DataIntegrityViolationException {
        Location location =locationJpaRepository.findById(locationId).orElseThrow(LocationNotFoundException::new);
        locationJpaRepository.delete(location);
    }

}
