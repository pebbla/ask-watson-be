package com.apebble.askwatson.cafe.location;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"위치"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class LocationController {
    private final LocationService locationService;
    private final ResponseService responseService;

    // 위치 등록
    @PostMapping(value="/admin/locations")
    public SingleResponse<Location> createLocation(@RequestBody LocationParams params) {
        return responseService.getSingleResponse(locationService.createLocation(params));
    }

    // 위치 목록 전체 조회
    @GetMapping(value = "/locations")
    public ListResponse<Location> getCompanies() {
        return responseService.getListResponse(locationService.getLocations());
    }

    // 위치 수정
    @PutMapping(value = "/admin/locations/{locationId}")
    public SingleResponse<Location> modifyLocation(@PathVariable Long locationId, @RequestBody LocationParams params) {
        return responseService.getSingleResponse(locationService.modifyLocation(locationId, params));
    }

    // 위치 삭제
    @DeleteMapping(value = "/admin/locations/{locationId}")
    public CommonResponse deleteTheme(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);
        return responseService.getSuccessResponse();
    }
}
