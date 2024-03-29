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
    private final FaqQueryRepository faqQueryRepository;
    private final ResponseService responseService;

    // 자주묻는질문 등록
    @PostMapping(value="/admin/faqs")
    public SingleResponse<FaqDto.Response> createFaq(@RequestBody FaqDto.Request params) {
        Long faqId = faqService.createFaq(params);
        return responseService.getSingleResponse(new FaqDto.Response(faqService.getOneFaq(faqId)));
    }

    // 자주묻는질문 전체 조회
    @GetMapping(value="/faqs")
    public ListResponse<FaqQueryDto.Response> getFaqs(@RequestParam(required = false) String searchWord) {
        return responseService.getListResponse(faqQueryRepository.findFaqsBySearchWord(searchWord));
    }

    // 자주묻는질문 단건 조회
    @GetMapping(value = "/faqs/{faqId}")
    public SingleResponse<FaqDto.Response> getFaq(@PathVariable Long faqId) {
        return responseService.getSingleResponse(new FaqDto.Response(faqService.getOneFaq(faqId)));
    }

    // 자주묻는질문 수정
    @PutMapping(value = "/admin/faqs/{faqId}")
    public SingleResponse<FaqDto.Response> modifyFaq(@PathVariable Long faqId, @RequestBody FaqDto.Request params) {
        faqService.modifyFaq(faqId, params);
        return responseService.getSingleResponse(new FaqDto.Response(faqService.getOneFaq(faqId)));
    }

    // 자주묻는질문 삭제
    @DeleteMapping(value = "/admin/faqs/{faqId}")
    public CommonResponse deleteCafe(@PathVariable Long faqId) {
        faqService.deleteFaq(faqId);
        return responseService.getSuccessResponse();
    }

}
