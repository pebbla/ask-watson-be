package com.apebble.askwatson.faq;
import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = {"자주묻는질문"})
@RequestMapping(value = "/v1")
public class FaqController {

    private final FaqService faqService;
    private final ResponseService responseService;

    // 자주묻는질문 등록
    @PostMapping(value="/admin/faqs")
    public SingleResponse<Long> createFaq(@RequestBody FaqParams params) {
        return responseService.getSingleResponse(faqService.createFaq(params));
    }

    // 자주묻는질문 전체 조회
    @GetMapping(value="/faqs")
    public ListResponse<FaqDto.Response> getFaqs(@RequestParam(required = false) String searchWord) {
        return responseService.getListResponse(faqService.getFaqs(searchWord));
    }

    // 자주묻는질문 단건 조회
    @GetMapping(value = "/faqs/{faqId}")
    public SingleResponse<FaqDto.Response> getFaq(@PathVariable Long faqId) {
        return responseService.getSingleResponse(faqService.getOneFaq(faqId));
    }

    // 자주묻는질문 수정
    @PutMapping(value = "/admin/faqs/{faqId}")
    public CommonResponse modifyFaq(@PathVariable Long faqId, @RequestBody FaqParams params) {
        faqService.modifyFaq(faqId, params);
        return responseService.getSuccessResponse();
    }

    // 자주묻는질문 삭제
    @DeleteMapping(value = "/admin/faqs/{faqId}")
    public CommonResponse deleteCafe(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return responseService.getSuccessResponse();
    }
}
