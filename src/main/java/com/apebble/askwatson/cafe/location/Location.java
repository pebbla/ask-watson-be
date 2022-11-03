package com.apebble.askwatson.cafe.location;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk

    private String state;                   // 대분류(행정구역)

    private String city;                    // 소분류(도시명)

    public void update(LocationParams params) {
        this.state = params.getState();
        this.city = params.getCity();
    }
}
