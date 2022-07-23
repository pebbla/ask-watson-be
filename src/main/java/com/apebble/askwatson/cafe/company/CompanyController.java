package com.apebble.askwatson.cafe.company;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ListResponse<Company> getCategories() {
        return responseService.getListResponse(companyService.getCategories());
    }

    // 회사 수정
    @PutMapping(value = "/admin/companies/{companyId}")
    public SingleResponse<Company> modifyCompany(@PathVariable Long companyId, @ModelAttribute CompanyParams params) {
        return responseService.getSingleResponse(companyService.modifyCompany(companyId, params));
    }

    // 회사 삭제
    @DeleteMapping(value = "/admin/companies/{companyId}")
    public CommonResponse deleteTheme(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
        return responseService.getSuccessResponse();
    }
}
