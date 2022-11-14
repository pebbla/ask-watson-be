package com.apebble.askwatson.report;

import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.review.Review;
import com.apebble.askwatson.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Report extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk

    @OneToOne(fetch = LAZY) @JoinColumn(name = "reporter_id")
    private User reporter;                  // 신고한 회원

    @OneToOne(fetch = LAZY) @JoinColumn(name = "reported_user_id")
    private User reportedUser;              // 신고당한 회원

    @Column(length = 100)
    private String content;                 // 신고 내용

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "review_id")
    private Review review;                  // 신고당한 리뷰

    private String reviewContent;           // 신고당한 리뷰 내용

    @Builder.Default @ColumnDefault("0")
    private boolean handledYn=false;        // 관리자 처리 여부



    //==연관관계 메서드==//
    public void deleteReview() {
        this.review = null;
    }

    //==조회 로직==//
    public boolean isReviewNull(){
        return this.review == null;
    }

    //==수정 로직==//
    public void updateHandledYn(boolean value) { this.handledYn = value; }
    public void deleteReporter() { this.reporter = null; }
    public void deleteReportedUser() { this.reportedUser = null; }

}
