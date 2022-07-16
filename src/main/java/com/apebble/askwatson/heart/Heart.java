package com.apebble.askwatson.heart;

import lombok.*;

import javax.persistence.*;

import com.apebble.askwatson.theme.Theme;

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

    private Long userId;            //유저 아이디

    @ManyToOne                      // 테마 아이디
    @JoinColumn(name = "theme_id")
    private Theme theme;    
    
}
