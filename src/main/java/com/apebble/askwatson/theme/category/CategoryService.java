package com.apebble.askwatson.theme.category;

import com.apebble.askwatson.comm.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryJpaRepository categoryJpaRepository;

    // 카테고리 등록
    public Category createCategory(CategoryParams params) {
        Category category = Category.builder()
                .categoryName(params.getCategoryName())
                .build();

        return categoryJpaRepository.save(category);
    }

    // 카테고리 전체 조회
    public List<Category> getCategories() {
        return categoryJpaRepository.findAll();
    }

    // 카테고리 수정
    public Category modifyCategory(Long categoryId, CategoryParams params) {
        Category category = categoryJpaRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        category.setCategoryName(params.getCategoryName());

        return category;
    }

    // 카테고리 삭제
    public void deleteCategory(Long categoryId) {
        Category category = categoryJpaRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        categoryJpaRepository.delete(category);
    }
}