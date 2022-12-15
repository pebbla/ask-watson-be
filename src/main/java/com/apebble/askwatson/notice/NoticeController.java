package com.apebble.askwatson.notice;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"공지사항"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class NoticeController {

    private final NoticeService noticeService;
    private final ResponseService responseService;

    // 공지사항 등록
    @PostMapping(value="/admin/notices")
    public SingleResponse<Long> createNotice(@RequestBody NoticeDto.Params params) {
        return responseService.getSingleResponse(noticeService.createNotice(params));
    }

    // 공지사항 전제 조회
    @GetMapping(value="/notices")
    public ListResponse<NoticeDto.Response> getNotices(@RequestParam(required = false) String searchWord) {
        return responseService.getListResponse(noticeService.getNotices(searchWord));
    }

    // 공지사항 단건 조회
    @GetMapping(value="/notices/{noticeId}")
    public SingleResponse<NoticeDto.Response> getOneNotice(@PathVariable Long noticeId) {
        return responseService.getSingleResponse(noticeService.getOneNotice(noticeId));
    }

    // 공지사항 수정
    @PutMapping(value = "/admin/notices/{noticeId}")
    public CommonResponse modifyNotice(@PathVariable Long noticeId, @RequestBody NoticeDto.Params params) {
        noticeService.modifyNotice(noticeId, params);
        return responseService.getSuccessResponse();
    }

    // 공지사항 삭제
    @DeleteMapping(value = "/admin/notices/{noticeId}")
    public CommonResponse deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return responseService.getSuccessResponse();
    }

}
