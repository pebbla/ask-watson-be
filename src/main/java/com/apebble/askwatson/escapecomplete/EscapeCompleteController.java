package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import com.apebble.askwatson.comm.util.DateConverter;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"탈출완료"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class EscapeCompleteController {

    private final ResponseService responseService;
    private final EscapeCompleteService escapeCompleteService;

    // 유저 탈출 완료
    @PostMapping(value = "/user/{userId}/themes/{themeId}/escape-completes")
    public CommonResponse createEscapeComplete(@PathVariable Long userId, @PathVariable Long themeId) {
        escapeCompleteService.createEscapeComplete(userId, themeId);
        return responseService.getSuccessResponse();
    }

    // 사용자별 탈출완료 목록 조회
    @GetMapping(value = "/user/{userId}/escape-completes")
    public ListResponse<EscapeCompleteDto.Response> getEscapeCompletesByUser(@PathVariable Long userId) {
        return responseService.getListResponse(escapeCompleteService.getEscapeCompletesByUserId(userId));
    }

    // 탈출 완료 일시 수정
    @PatchMapping(value = "/user/escape-completes/{escapeCompleteId}")
    public CommonResponse modifyEscapeCompleteDt(@PathVariable Long escapeCompleteId, @RequestParam(name="escapeCompleteDt") String escapeCompleteDtStr) {
        escapeCompleteService.modifyEscapeCompleteDt(escapeCompleteId, DateConverter.strToLocalDate(escapeCompleteDtStr));
        return responseService.getSuccessResponse();
    }

    // 탈출 완료 취소
    @DeleteMapping(value = "/user/escape-completes/{escapeCompleteId}")
    public CommonResponse cancelEscapeComplete(@PathVariable Long escapeCompleteId) {
        escapeCompleteService.deleteEscapeCompleteByCheckingReview(escapeCompleteId);
        return responseService.getSuccessResponse();
    }

}
