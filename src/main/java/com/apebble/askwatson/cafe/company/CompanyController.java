package com.apebble.askwatson.cafe.company;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"회사"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CompanyController {
    private final CompanyService companyService;
    private final ResponseService responseService;

    // 회사 등록
    @PostMapping(value="/admin/companies")
    public SingleResponse<Company> createCompany(@ModelAttribute CompanyParams params) {
        return responseService.getSingleResponse(companyService.createCompany(params));
    }

    // 회사 목록 전체 조회
    @GetMapping(value = "/companies")
    public ListResponse<Company> getCompanies() {
        return responseService.getListResponse(companyService.getCompanies());
    }

    // 회사 수정
    @PutMapping(value = "/admin/companies/{companyId}")
    public SingleResponse<Company> modifyCompany(@PathVariable Long companyId, @ModelAttribute CompanyParams params) {
        return responseService.getSingleResponse(companyService.modifyCompany(companyId, params));
    }

    // 회사 삭제
    @DeleteMapping(value = "/admin/companies/{companyId}")
    public CommonResponse deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return responseService.getSuccessResponse();
    }
}