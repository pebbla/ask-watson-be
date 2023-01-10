package com.apebble.askwatson.suggestion;

import com.apebble.askwatson.comm.response.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"건의"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class SuggestionController {

    private final SuggestionService suggestionService;
    private final SuggestionQueryRepository suggestionQueryRepository;
    private final ResponseService responseService;

    // 건의 등록
    @PostMapping(value = "/user/{userId}/cafes/{cafeId}/suggestions")
    public SingleResponse<SuggestionDto.Response> createSuggestion(@PathVariable Long userId,
                                                                   @PathVariable Long cafeId,
                                                                   @RequestBody SuggestionDto.Request params) {
        Long suggestionId = suggestionService.createSuggestion(cafeId, userId, params);
        return responseService.getSingleResponse(new SuggestionDto.Response(suggestionService.getOneSuggestion(suggestionId)));
    }

    // 건의 목록 조회
    @GetMapping(value = "/admin/suggestions")
    public ListResponse<SuggestionQueryDto.Response> getSuggestions(@RequestParam(required = false) String searchWord,
                                                                    @RequestParam(required = false) Boolean handledYn) {
        return responseService.getListResponse(suggestionQueryRepository.getSuggestions(searchWord, handledYn));
    }

    // 건의 처리 상태 변경
    @PatchMapping(value = "/admin/suggestions/{suggestionId}")
    public CommonResponse modifySuggestionHandledYn(@PathVariable Long suggestionId, @RequestParam Boolean handledYn) {
        suggestionService.modifySuggestionHandledYn(suggestionId, handledYn);
        return responseService.getSuccessResponse();
    }

    // 건의 삭제
    @DeleteMapping(value = "/admin/suggestions/{suggestionId}")
    public CommonResponse deleteSuggestion(@PathVariable Long suggestionId) {
        suggestionService.deleteSuggestion(suggestionId);
        return responseService.getSuccessResponse();
    }

}
