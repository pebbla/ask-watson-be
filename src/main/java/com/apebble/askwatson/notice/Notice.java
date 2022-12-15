package com.apebble.askwatson.notice;

import com.apebble.askwatson.comm.BaseTime;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // pk
    private String title;               // 제목
    @Column(length = 2000)
    private String content;             // 내용


    //==생성 메서드==//
    public static Notice create(NoticeDto.Params params) {
        Notice notice = new Notice();
        notice.title = params.getTitle();
        notice.content = params.getContent();
        return notice;
    }


    //==수정 로직==//
    public void update(NoticeDto.Params params) {
        this.title = params.getTitle();
        this.content = params.getContent();
    }

}
