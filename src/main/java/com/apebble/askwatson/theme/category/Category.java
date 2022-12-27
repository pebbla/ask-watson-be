package com.apebble.askwatson.theme.category;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk
    private String categoryName;            // 카테고리명


    //==생성 메서드==//
    public static Category create(CategoryParams params) {
        Category category = new Category();
        category.categoryName = params.getCategoryName();
        return category;
    }


    //==수정 로직==//
    public void update(CategoryParams params) {
        this.categoryName = params.getCategoryName();
    }
}
