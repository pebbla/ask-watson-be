package com.apebble.askwatson.theme.category;

import com.apebble.askwatson.comm.response.CommonResponse;
import com.apebble.askwatson.comm.response.ListResponse;
import com.apebble.askwatson.comm.response.ResponseService;
import com.apebble.askwatson.comm.response.SingleResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"카테고리"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class CategoryController {
    private final CategoryService categoryService;
    private final ResponseService responseService;

    // 카테고리 등록
    @PostMapping(value="/admin/categories")
    public SingleResponse<Category> createCategory(@RequestBody CategoryParams params) {
        return responseService.getSingleResponse(categoryService.createCategory(params));
    }

    // 카테고리 목록 전체 조회
    @GetMapping(value = "/categories")
    public ListResponse<Category> getCategories() {
        return responseService.getListResponse(categoryService.getCategories());
    }

    // 카테고리 수정
    @PutMapping(value = "/admin/categories/{categoryId}")
    public SingleResponse<Category> modifyCategory(@PathVariable Long categoryId, @RequestBody CategoryParams params) {
        return responseService.getSingleResponse(categoryService.modifyCategory(categoryId, params));
    }

    // 카테고리 삭제
    @DeleteMapping(value = "/admin/categories/{categoryId}")
    public CommonResponse deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return responseService.getSuccessResponse();
    }
}
