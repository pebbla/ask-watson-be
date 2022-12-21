package com.apebble.askwatson.faq;

import com.apebble.askwatson.comm.BaseTime;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Faq extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // pk
    private String title;           // 제목
    @Column(length = 2000)
    private String content;         // 내용


    //==생성 메서드==//
    public static Faq create(FaqDto.Request params) {
        Faq faq = new Faq();
        faq.title = params.getTitle();
        faq.content = params.getContent();
        return faq;
    }


    //==수정 로직==//
    public void update(FaqDto.Request params) {
        this.title = params.getTitle();
        this.content = params.getContent();
    }

}
