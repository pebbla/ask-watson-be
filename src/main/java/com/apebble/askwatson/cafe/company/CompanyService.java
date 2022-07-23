package com.apebble.askwatson.cafe.company;

import com.apebble.askwatson.comm.exception.CompanyNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyJpaRepository companyJpaRepository;

    // 회사 등록
    public Company createCompany(CompanyParams params) {
        Company company = Company.builder()
                .companyName(params.getCompanyName())
                .build();

        return companyJpaRepository.save(company);
    }

    // 회사 전체 조회
    public List<Company> getCategories() {
        return companyJpaRepository.findAll();
    }

    // 회사 수정
    public Company modifyCompany(Long companyId, CompanyParams params) {
        Company company =companyJpaRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
       company.setCompanyName(params.getCompanyName());

        return company;
    }

    // 회사 삭제
    public void deleteCompany(Long companyId) {
        Company company =companyJpaRepository.findById(companyId).orElseThrow(CompanyNotFoundException::new);
       companyJpaRepository.delete(company);
    }
}
