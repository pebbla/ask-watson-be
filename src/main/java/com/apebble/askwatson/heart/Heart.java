package com.apebble.askwatson.heart;

import lombok.*;

import javax.persistence.*;

import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // pk

    @ManyToOne @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne                      // 테마 아이디
    @JoinColumn(name = "theme_id")
    private Theme theme;    
    
}
