package com.apebble.askwatson.report;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ReportController {
    private final ResponseService responseService;
    private final ReportService reportService;

    // 신고 등록
    @PostMapping(value = "/user/{userId}/reviews/{reviewId}/reports")
    public SingleResponse<Report> createReport(@PathVariable Long userId, @PathVariable Long reviewId, @ModelAttribute ReportParams params) {
        return responseService.getSingleResponse(reportService.createReport(userId, reviewId, params));
    }

    // 신고 전체 조회
    @GetMapping(value = "/reports")
    public ListResponse<Report> getAllReports() {
        return responseService.getListResponse(reportService.getAllReports());
    }

    // 처리 안된 신고 목록 조회
    @GetMapping(value = "/reports/unhandled")
    public ListResponse<Report> getUnhandledReports() {
        return responseService.getListResponse(reportService.getUnhandledReports());
    }

    // 신고 처리 상태 변경
    @PatchMapping(value = "/admin/reports/{reportId}")
    public CommonResponse modifyReportHandledYn(@PathVariable Long reportId, @RequestParam(name="handledyn") Boolean handledYn) {
        reportService.modifyReportHandledYn(reportId, handledYn);
        return responseService.getSuccessResponse();
    }
}
