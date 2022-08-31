package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.company.Company;
import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.comm.util.GeographyConverter;
import com.apebble.askwatson.theme.Theme;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;

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
    private Long id;                                            // pk

    @Column(length = 64)
    private String cafeName;                        // 방탈출카페명

    private String cafePhoneNum;                                // 방탈출카페 전화번호

    @ManyToOne @JoinColumn(name = "location_id")
    private Location location;                                  // 방탈출카페 지역

    @ManyToOne @JoinColumn(name = "company_id")
    private Company company;                                    // 방탈출카페 체인명

    private String website;                                     // 방탈출카페 웹사이트

    private String address;                                     // 방탈출카페 주소

    private String imageUrl;                                    // 방탈출카페 이미지 url

    @Column(nullable = false, columnDefinition = "GEOMETRY")
    private Point geography;                                    // 방탈출카페 위치정보(경도, 위도)

    @Builder.Default @ColumnDefault("0")
    private int reviewCount=0;                                  // 리뷰 수

    @Builder.Default @ColumnDefault("0")
    private double rating=0;                                    // 평균 별점

    @Builder.Default @ColumnDefault("0")
    private boolean isEnglishPossible=false;                    // 영어 가능 여부

    @Singular("theme")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cafe", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Theme> themeList = new ArrayList<>();          // 방탈출 테마 리스트(fk)

    public void addTheme(Theme theme) {
        this.themeList.add(theme);
        if(theme.getCafe() != this) theme.setCafe(this);
    }

    public void updateCafeByReview(double newRating) {
        this.rating = newRating;
    }

    public void incReviewCount() {
        this.reviewCount++;
    }

    public void decReviewCount() {
        this.reviewCount--;
    }

    public void update(CafeParams params, Location location, Company company) throws ParseException{
        this.cafeName = params.getCafeName();
        this.cafePhoneNum = params.getCafePhoneNum();
        this.location = location;
        this.company = company;
        this.website = params.getWebsite();
        this.address = params.getAddress();
        this.imageUrl = params.getImageUrl();
        this.geography = GeographyConverter.strToPoint(params.getLongitude(), params.getLatitude());
        this.isEnglishPossible = params.getIsEnglishPossible();
    }
}
