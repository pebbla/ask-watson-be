package com.apebble.askwatson.like;

import lombok.*;

import javax.persistence.*;

import com.apebble.askwatson.theme.Theme;
import com.apebble.askwatson.user.User;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // pk

    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private User user;
    private Long userId;            //유저 아이디

    @ManyToOne                      // 테마 아이디
    @JoinColumn(name = "theme_id")
    private Theme theme;    
    
}
