package com.apebble.askwatson.report;

import lombok.Data;

@Data
public class ReportParams {
    private Long reportedUserId;       // 신고당한 회원

    private String content;            // 신고 내용
}
