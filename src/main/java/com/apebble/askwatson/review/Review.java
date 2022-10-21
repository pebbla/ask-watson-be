package com.apebble.askwatson.review;

import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.escapecomplete.EscapeComplete;
import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // pk

    @ManyToOne                      // 유저 아이디 
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;    
    
    private double difficulty;      // 난이도

    private double timeTaken;       // 걸린시간

    private Integer usedHintNum;    // 사용한 힌트 갯수

    private double rating;          // 별점

    private double deviceRatio;             // 장치 비율(1, 3, 5)

    private double activity;                // 활동성(1, 3, 5)

    private String content;         // 내용

    @ManyToOne                      // 테마 
    @JoinColumn(name = "theme_id")
    @JsonIgnore
    private Theme theme;

    @OneToOne @JoinColumn(name = "escape_complete_id")
    private EscapeComplete escapeComplete;

    public boolean isUserNull(){
        return this.user == null;
    }

    public void update(ReviewParams params) {
       this.difficulty = params.getDifficulty();
       this.timeTaken = params.getTimeTaken();
       this.usedHintNum = params.getUsedHintNum();
       this.rating = params.getRating();
       this.deviceRatio = params.getDeviceRatio();
       this.activity = params.getActivity();
       this.content = params.getContent();
    }
}
