package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.location.Location;
import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.theme.Theme;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cafe extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                            // pk

    @Column(length = 64)
    private String cafeName;                                    // 방탈출카페명
    private String cafePhoneNum;                                // 방탈출카페 전화번호
    private String website;                                     // 방탈출카페 웹사이트
    private String address;                                     // 방탈출카페 주소
    @Column(length = 400)
    private String imageUrl;                                    // 방탈출카페 이미지 url
    @ColumnDefault("0")
    private Boolean isEnglishPossible=false;                    // 영어 가능 여부
    @ColumnDefault("1")
    private boolean isAvailable=true;                           // 카페 이용가능 여부

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "location_id")
    private Location location;                                  // 방탈출카페 지역
    @Column(columnDefinition = "GEOMETRY")
    private Point geography;                                    // 방탈출카페 위치정보(경도, 위도)

    @ColumnDefault("0")
    private int reviewCount=0;                                  // 리뷰 수
    @ColumnDefault("0")
    private double rating=0;                                    // 평균 별점

    @Singular("theme")
    @OneToMany(fetch = LAZY, mappedBy = "cafe", cascade = ALL)
    private List<Theme> themeList = new ArrayList<>();          // 방탈출 테마 리스트(fk)


    //==연관관계 편의 메서드==//
    public void addTheme(Theme theme) {
        this.themeList.add(theme);
        if(theme.getCafe() != this) theme.setCafe(this);
    }


    //==생성 메서드==//
    public static Cafe create(CafeParams params, Location location, Point geography) {
        Cafe cafe = new Cafe();
        cafe.cafeName = params.getCafeName();
        cafe.cafePhoneNum = params.getCafePhoneNum();
        cafe.location = location;
        cafe.website = params.getWebsite();
        cafe.address = params.getAddress();
        cafe.imageUrl = params.getImageUrl();
        cafe.geography = geography;
        cafe.isEnglishPossible = params.getIsEnglishPossible();
        cafe.isAvailable = params.getIsAvailable();
        return cafe;
    }


    //==조회 로직==//
    public boolean isLocationNull(){
        return this.location == null;
    }
    public boolean isGeographyNull(){
        return this.geography == null;
    }


    //==수정 로직==//
    public void updateRating(double newRating) { this.rating = newRating; }
    public void updateImageUrl(String url) { this.imageUrl = url; }
    public void incReviewCount() { this.reviewCount++; }
    public void decReviewCount() { this.reviewCount--; }

    public void update(CafeParams params, Location location, Point geography) {
        this.cafeName = params.getCafeName();
        this.cafePhoneNum = params.getCafePhoneNum();
        this.location = location;
        this.website = params.getWebsite();
        this.address = params.getAddress();
        this.imageUrl = params.getImageUrl();
        this.geography = geography;
        this.isEnglishPossible = params.getIsEnglishPossible();
        this.isAvailable = params.getIsAvailable();
    }


    //==비즈니스 로직==//
    public void deleteUselessInfo() {
        this.isAvailable = false;
        this.cafePhoneNum = null;
        this.location = null;
        this.website = null;
        this.address = null;
        this.imageUrl = null;
        this.geography = null;
        this.isEnglishPossible = null;
    }
}
