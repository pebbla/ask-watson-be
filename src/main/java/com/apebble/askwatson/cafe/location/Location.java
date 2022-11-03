package com.apebble.askwatson.cafe.location;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk
    private String state;                   // 대분류(행정구역)
    private String city;                    // 소분류(도시명)


    //==수정 로직==//
    public void update(LocationParams params) {
        this.state = params.getState();
        this.city = params.getCity();
    }

}
