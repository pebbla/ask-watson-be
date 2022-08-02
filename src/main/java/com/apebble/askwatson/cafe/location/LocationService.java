package com.apebble.askwatson.cafe.location;

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
    public Location createLocation(LocationParams params) {
        Location location = Location.builder()
                .state(params.getState())
                .city(params.getCity())
                .build();

        return locationJpaRepository.save(location);
    }

    // 위치 전체 조회
    public List<Location> getLocations() {
        return locationJpaRepository.findAll();
    }

    // 위치 수정
    public Location modifyLocation(Long locationId, LocationParams params) {
        Location location =locationJpaRepository.findById(locationId).orElseThrow(LocationNotFoundException::new);
        location.setState(params.getState());
        location.setCity(params.getCity());
        return location;
    }

    // 위치 삭제
    public void deleteLocation(Long locationId) {
        Location location =locationJpaRepository.findById(locationId).orElseThrow(LocationNotFoundException::new);
        locationJpaRepository.delete(location);
    }
}
