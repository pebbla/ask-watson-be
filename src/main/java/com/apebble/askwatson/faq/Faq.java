package com.apebble.askwatson.faq;

import com.apebble.askwatson.comm.BaseTime;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Faq extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // pk

    private String title;           // 제목
    @Column(length = 2000)
    private String content;         // 내용


    //==수정 로직==//
    public void update(FaqParams params) {
        this.title = params.getTitle();
        this.content = params.getContent();
    }

}
