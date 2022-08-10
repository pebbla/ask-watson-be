package com.apebble.askwatson.cafe;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public class CafeSearchOptions {
    private List<Long> companyIds;                  // 회사별 조건

    private Long locationId;                        // 위치별 조건

    private Boolean isEnglishPossible;              // 영어 가능

    public int getCompanyIdsSize() {
        return companyIds == null || CollectionUtils.isEmpty(companyIds)
                ? 0 : companyIds.size();
    }
}
