package com.apebble.askwatson.escapecomplete;

import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EscapeComplete {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate escapeCompleteDt;     // 탈출 완료 일시 (Default: 오늘 날짜)

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;                      // 탈출 완료한 회원

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;                    // 탈출 완료한 테마


    //==수정 로직==//
    public void update(LocalDate escapeCompleteDt) {
        this.escapeCompleteDt = escapeCompleteDt;
    }

}
