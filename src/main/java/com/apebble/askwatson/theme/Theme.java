package com.apebble.askwatson.theme;

import com.apebble.askwatson.cafe.Cafe;
import com.apebble.askwatson.comm.BaseTime;
import com.apebble.askwatson.theme.category.Category;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Theme extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                // pk

    @Column(length = 64)
    private String themeName;                       // 테마명
    @Column(length = 1000, nullable = false)
    private String themeExplanation;                // 테마 설명
    private Integer timeLimit;                      // 제한시간
    private Integer minNumPeople;                   // 최소 인원 수
    private Integer price;                          // 가격
    @Column(length = 400)
    private String reservationUrl;                  // 예약하기 url
    @Column(length = 400)
    private String imageUrl;                        // 방탈출테마 이미지 url
    @ColumnDefault("1")
    private boolean isAvailable=true;               // 테마 이용가능 여부

    @ColumnDefault("0")
    private double difficulty = 0;                      // 난이도
    @ColumnDefault("0")
    private int heartCount=0;                       // 좋아요 수
    @ColumnDefault("0")
    private int escapeCount=0;                      // 탈출 횟수
    @ColumnDefault("0")
    private int reviewCount=0;                      // 리뷰 수
    @ColumnDefault("0")
    private double rating=0;                        // 평균 별점
    @ColumnDefault("0")
    private double deviceRatio=0;                   // 장치 비율(적음, 보통, 많음)
    @ColumnDefault("0")
    private double activity=0;                      // 활동성(낮음, 보통, 높음)

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "category_id")
    private Category category;                      // 카테고리

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;                              // 방탈출 카페


    //==연관관계 편의 메서드==//
    public void setCafe(Cafe cafe) {
        this.cafe = cafe;

        if(!cafe.getThemeList().contains(this))
            cafe.getThemeList().add(this);
    }


    //==생성 메서드==//
    public static Theme create(Cafe cafe, Category category, ThemeDto.Request params) {
        Theme theme = new Theme();
        theme.themeName = params.getThemeName();
        theme.themeExplanation = params.getThemeExplanation();
        theme.timeLimit = params.getTimeLimit();
        theme.minNumPeople = params.getMinNumPeople();
        theme.price = params.getPrice();
        theme.reservationUrl = params.getReservationUrl();
        theme.imageUrl = params.getImageUrl();
        if(params.getIsAvailable() != null)
            theme.isAvailable = params.getIsAvailable();
        theme.cafe = cafe;
        theme.category = category;
        return theme;
    }


    //==조회 로직==//
    //==수정 로직==//
    public void incHeartCount() { this.heartCount++; }
    public void decHeartCount() { this.heartCount--; }
    public void incEscapeCount() { this.escapeCount++; }
    public void decEscapeCount() { this.escapeCount--; }
    public void incReviewCount() { this.reviewCount++; }
    public void decReviewCount() { this.reviewCount--; }
    public void updateAvailability(boolean value) {this.isAvailable = value;}
    public void updateImageUrl(String url) { this.imageUrl = url; }

    public void update(ThemeDto.Request params, Category category) {
        this.themeName = params.getThemeName();
        this.themeExplanation = params.getThemeExplanation();
        this.category = category;
        this.imageUrl = params.getImageUrl();
        this.timeLimit = params.getTimeLimit();
        this.minNumPeople = params.getMinNumPeople();
        this.price = params.getPrice();
        this.reservationUrl = params.getReservationUrl();
        if(params.getIsAvailable() != null)
            this.isAvailable = params.getIsAvailable();
    }

    public void updateByReview(double newRating, double newDeviceRatio, double newActivity) {
        this.rating = newRating;
        this.deviceRatio = newDeviceRatio;
        this.activity = newActivity;
    }

    //==비즈니스 로직==//

}
