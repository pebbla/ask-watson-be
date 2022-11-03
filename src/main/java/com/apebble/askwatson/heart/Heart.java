package com.apebble.askwatson.heart;

import lombok.*;

import javax.persistence.*;

import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static javax.persistence.FetchType.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Heart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // pk

    @ManyToOne(fetch = LAZY) @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;              // 회원

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;            // 테마
    
}
