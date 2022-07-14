package com.apebble.askwatson.review;

import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.theme.Theme;
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
    private Long id;                 // pk

    private double rating;           // 제목

    private String content;          // 내용

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
}
