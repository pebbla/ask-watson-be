package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscapeComplete {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk

    @OneToOne @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;                      // 탈출 완료한 회원

    @OneToOne @JoinColumn(name = "theme_id")
    private Theme theme;                    // 탈출 완료한 테마

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate escapeCompleteDt;     // 탈출 완료 일시 (Default: 오늘 날짜)
}
