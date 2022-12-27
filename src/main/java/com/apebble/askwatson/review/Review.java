package com.apebble.askwatson.review;

import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.check.Check;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Review extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk
    private double difficulty;              // 난이도
    private double timeTaken;               // 걸린시간
    private Integer usedHintNum;            // 사용한 힌트 갯수
    private double rating;                  // 별점
    private double deviceRatio;             // 장치 비율(1, 3, 5)
    private double activity;                // 활동성(1, 3, 5)
    private String content;                 // 내용

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;                      // 회원

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;                    // 테마

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "check_id")
    private Check check;  // 탈출완료


    //==생성 메서드==//
    public static Review create(User user, Theme theme, Check check, ReviewDto.Request params) {
        Review review = new Review();
        review.difficulty = params.getDifficulty();
        review.timeTaken = params.getTimeTaken();
        review.usedHintNum = params.getUsedHintNum();
        review.rating = params.getRating();
        review.deviceRatio = params.getDeviceRatio();
        review.activity = params.getActivity();
        review.content = params.getContent();
        review.user = user;
        review.theme = theme;
        review.check = check;
        return review;
    }


    //==조회 로직==//
    public boolean isUserNull(){
        return this.user == null;
    }


    //==수정 로직==//
    public void deleteUser() { this.user = null; }
    public void deleteCheck() { this.check = null; }
    public void update(ReviewDto.Request params) {
       this.difficulty = params.getDifficulty();
       this.timeTaken = params.getTimeTaken();
       this.usedHintNum = params.getUsedHintNum();
       this.rating = params.getRating();
       this.deviceRatio = params.getDeviceRatio();
       this.activity = params.getActivity();
       this.content = params.getContent();
    }

}
