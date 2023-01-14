package com.apebble.askwatson.suggestion;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Suggestion extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            // pk
    private String content;                     // 건의 내용
    @ColumnDefault("0")
    private boolean handledYn=false;            // 관리자 처리 여부

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;                          // 방탈출 카페

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;                          // 건의한 회원


    //==생성 메서드==//
    public static Suggestion create(SuggestionDto.Request params, Cafe cafe, User user) {
        Suggestion suggestion = new Suggestion();
        suggestion.content = params.getContent();
        suggestion.cafe = cafe;
        suggestion.user = user;
        return suggestion;
    }


    //==수정 로직==//
    public void updateHandledYn(boolean value) { this.handledYn = value; }
    public void deleteUser() { this.user = null; }

}
