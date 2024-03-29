package com.apebble.askwatson.check;

import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity(name ="checks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Check {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk

    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate checkDt;     // 탈출 완료 일시

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;                      // 탈출 완료한 회원

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;                    // 탈출 완료한 테마


    //==생성 메서드==//
    public static Check create(User user, Theme theme) {
        Check check = new Check();
        check.checkDt = LocalDate.now();
        check.user = user;
        check.theme = theme;
        return check;
    }


    //==수정 로직==//
    public void update(LocalDate checkDt) {
        this.checkDt = checkDt;
    }

}
