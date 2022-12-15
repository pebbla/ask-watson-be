package com.apebble.askwatson.theme.category;

import com.apebble.askwatson.comm.exception.CategoryNotFoundException;
import com.apebble.askwatson.comm.exception.DataIntegrityViolationException;

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
    public Long createCategory(CategoryParams params) {
        return categoryJpaRepository.save(Category.create(params)).getId();
    }

    // 카테고리 전체 조회
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryJpaRepository.findAll();
    }

    // 카테고리 수정
    public void modifyCategory(Long categoryId, CategoryParams params) {
        Category category = categoryJpaRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        category.update(params);
    }

    // 카테고리 삭제
    public void deleteCategory(Long categoryId) throws DataIntegrityViolationException {
        Category category = categoryJpaRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        categoryJpaRepository.delete(category);
    }

}
