package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.company.Company;
import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.theme.Theme;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cafe extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // pk

    @Column(length = 20)
    private String cafeName;                        // 방탈출카페명

    private String cafePhoneNum;                    // 방탈출카페 전화번호

    @ManyToOne @JoinColumn(name = "location_id")
    private Location location;                      // 방탈출카페 지역

    @ManyToOne @JoinColumn(name = "company_id")
    private Company company;                        // 방탈출카페 체인명

    @Column(nullable = false, columnDefinition = "GEOMETRY")
    private Point geography;                        // 방탈출카페 위치정보(경도, 위도)

    @Singular("theme")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cafe", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Theme> themeList = new ArrayList<>();      // 방탈출 테마 리스트(fk)

    public void addTheme(Theme theme) {
        this.themeList.add(theme);
        if(theme.getCafe() != this) theme.setCafe(this);
    }
}
