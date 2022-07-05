package com.apebble.askwatson.notice;
import com.apebble.askwatson.comm.BaseTime;
import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notice extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // pk

    private String title;               // 제목

    private String content;             // 내용
}
