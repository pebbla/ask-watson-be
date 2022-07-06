package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.comm.BaseTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Theme extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // pk

    @Column(length = 20)
    private String themeName;           // 테마명

    @Column(length = 100, nullable = false)
    private String themeExplanation;    // 테마 설명

    private String category;            // 카테고리

    private double difficulty;          // 난이도

    private int timeLimit;              // 제한시간

    @Builder.Default @ColumnDefault("0")
    private int likeCount=0;            // 좋아요 수

    @Builder.Default @ColumnDefault("0")
    private int escapeCount=0;          // 탈출 횟수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    @JsonBackReference
    private Cafe cafe;                  // 방탈출 카페

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;

        if(!cafe.getThemeList().contains(this))
            cafe.getThemeList().add(this);
    }
}
