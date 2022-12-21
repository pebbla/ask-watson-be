package com.apebble.askwatson.report;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"신고"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ReportController {

    private final ResponseService responseService;
    private final ReportService reportService;

    // 신고 등록
    @PostMapping(value = "/user/{userId}/reviews/{reviewId}/reports")
    public SingleResponse<ReportDto.Response> createReport(@PathVariable Long userId,
                                                           @PathVariable Long reviewId,
                                                           @RequestBody ReportDto.Request params) {
        Long reportId = reportService.createReport(userId, reviewId, params);
        return responseService.getSingleResponse(new ReportDto.Response(reportService.getOneReport(reportId)));
    }

    // 신고 목록 조회
    @GetMapping(value = "/admin/reports")
    public ListResponse<ReportDto.Response> getReports(@RequestParam(required = false) Boolean handledYn,
                                                       @RequestParam(required = false) String searchWord) {
        if(handledYn == null) {
            return responseService.getListResponse(reportService.getAllReports(searchWord));
        } else {
            return responseService.getListResponse(reportService.getReportsByHandledYn(searchWord, handledYn));
        }
    }

    // 신고 처리 상태 변경
    @PatchMapping(value = "/admin/reports/{reportId}")
    public CommonResponse modifyReportHandledYn(@PathVariable Long reportId, @RequestParam Boolean handledYn) {
        reportService.modifyReportHandledYn(reportId, handledYn);
        return responseService.getSuccessResponse();
    }

}
