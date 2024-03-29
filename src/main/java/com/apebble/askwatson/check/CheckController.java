package com.apebble.askwatson.check;

import com.apebble.askwatson.comm.response.*;
import com.apebble.askwatson.comm.util.DateConverter;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"탈출완료"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CheckController {

    private final ResponseService responseService;
    private final CheckService checkService;
    private final CheckQueryRepository checkQueryRepository;

    // 유저 탈출 완료
    @PostMapping(value = "/user/{userId}/themes/{themeId}/checks")
    public SingleResponse<Long> createCheck(@PathVariable Long userId, @PathVariable Long themeId) {
        Long checkId = checkService.createCheck(userId, themeId);
        return responseService.getSingleResponse(checkId);
    }

    // 사용자별 탈출완료 목록 조회
    @GetMapping(value = "/user/{userId}/checks")
    public PageResponse<CheckQueryDto.Response> getChecksByUser(@PathVariable Long userId, Pageable pageable) {
        return responseService.getPageResponse(checkQueryRepository.getChecksByUserId(userId, pageable));
    }

    // 탈출 완료 일시 수정
    @PatchMapping(value = "/user/checks/{checkId}")
    public SingleResponse<CheckDto.SimpleResponse> modifyCheckDt(@PathVariable Long checkId,
                                                                 @RequestParam(name="checkDt") String checkDtStr) {
        checkService.modifyCheckDt(checkId, DateConverter.strToLocalDate(checkDtStr));
        return responseService.getSingleResponse(new CheckDto.SimpleResponse(checkService.findOneWithTheme(checkId)));
    }

    // 탈출 완료 취소
    @DeleteMapping(value = "/user/checks/{checkId}")
    public CommonResponse cancelCheck(@PathVariable Long checkId) {
        checkService.deleteCheckIfNoReview(checkId);
        return responseService.getSuccessResponse();
    }

}
