package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Api(tags = {"탈출완료"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class EscapeCompleteController {
    private final ResponseService responseService;
    private final EscapeCompleteService escapeCompleteService;

    // 유저 탈출 완료
    @PostMapping(value = "/user/{userId}/themes/{themeId}")
    public SingleResponse<EscapeComplete> createEscapeComplete(@PathVariable Long userId, @PathVariable Long themeId) {
        return responseService.getSingleResponse(escapeCompleteService.createEscapeComplete(userId, themeId));
    }

    // 사용자별 탈출완료 목록 조회
    @GetMapping(value = "/user/{userId}/escapecompletes")
    public ListResponse<EscapeComplete> getEscapeCompletesByUser(@PathVariable Long userId) {
        return responseService.getListResponse(escapeCompleteService.getEscapeCompletesByUserId(userId));
    }

    // 탈출 완료 일시 수정
    @PatchMapping(value = "/user/escapecompletes/{escapeCompleteId}")
    public SingleResponse<EscapeComplete> modifyEscapeCompleteDt(@PathVariable Long escapeCompleteId, @RequestParam(name="escapeCompleteDt") String escapeCompleteDtStr) {
        LocalDate escapeCompleteDt = LocalDate.parse(escapeCompleteDtStr, DateTimeFormatter.ISO_DATE);
        return responseService.getSingleResponse(escapeCompleteService.modifyEscapeCompleteDt(escapeCompleteId, escapeCompleteDt));
    }

    // 탈출 완료 취소
    @DeleteMapping(value = "/user/escapecompletes/{escapeCompleteId}")
    public CommonResponse cancelEscapeComplete(@PathVariable Long escapeCompleteId) {
        escapeCompleteService.deleteEscapeComplete(escapeCompleteId);
        return responseService.getSuccessResponse();
    }
}
