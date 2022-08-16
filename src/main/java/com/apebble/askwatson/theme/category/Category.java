package com.apebble.askwatson.theme.category;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // pk

    private String categoryName;            // 카테고리명

    public void update(CategoryParams params) {
        this.categoryName = params.getCategoryName();
    }
}
