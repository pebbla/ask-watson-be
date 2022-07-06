package com.apebble.askwatson.notice;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class NoticeController {

    private final NoticeService noticeService;
    private final ResponseService responseService;

    // 공지사항 등록
    @PostMapping(value="/admin/notice")
    public SingleResponse<Notice> createNotice(@ModelAttribute NoticeParams params) {
        return responseService.getSingleResponse(noticeService.createNotice(params));
    }

    // 공지사항 전제 조회
    @GetMapping(value="/notices")
    public ListResponse<Notice> getNotices() {
        return responseService.getListResponse(noticeService.getNotices());
    }

    // 공지사항 단건 조회
    @GetMapping(value="/notice/{noticeId}")
    public SingleResponse<Notice> getOneNotice(@PathVariable Long noticeId) {
        return responseService.getSingleResponse(noticeService.getOneNotice(noticeId));
    }

    // 공지사항 수정
    @PutMapping(value = "/admin/notice/{noticeId}")
    public SingleResponse<Notice> modifyNotice(@PathVariable Long noticeId, @ModelAttribute NoticeParams params) {
        return responseService.getSingleResponse(noticeService.modifyNotice(noticeId, params));
    }

    // 공지사항 삭제
    @DeleteMapping(value = "/admin/notice/{noticeId}")
    public CommonResponse deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return responseService.getSuccessResponse();
    }

}
