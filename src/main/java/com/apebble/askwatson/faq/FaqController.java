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
    public SingleResponse<Faq> createFaq(@ModelAttribute FaqParams params) {
        return responseService.getSingleResponse(faqService.createFaq(params));
    }

    // 자주묻는질문 전체 조회
    @GetMapping(value="/faqs")
    public ListResponse<Faq> getFaqs() {
        return responseService.getListResponse(faqService.getFaqs());
    }

    // 자주묻는질문 단건 조회
    @GetMapping(value = "/faqs/{faqId}")
    public SingleResponse<Faq> getFaq(@PathVariable Long faqId) {
        return responseService.getSingleResponse(faqService.getOneFaq(faqId));
    }

    // 자주묻는질문 수정
    @PutMapping(value = "/faqs/{faqId}")
    public SingleResponse<Faq> modifyFaq(@PathVariable Long faqId, @ModelAttribute FaqParams params) {
        return responseService.getSingleResponse( faqService.modifyFaq(faqId, params));
    }

    // 자주묻는질문 삭제
    @DeleteMapping(value = "/admin/faqs/{faqId}")
    public CommonResponse deleteCafe(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return responseService.getSuccessResponse();
    }
}
