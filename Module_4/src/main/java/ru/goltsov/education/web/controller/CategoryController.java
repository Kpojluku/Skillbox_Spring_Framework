package ru.goltsov.education.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.goltsov.education.mapper.CategoryMapper;
import ru.goltsov.education.service.CategoryService;
import ru.goltsov.education.web.model.request.CategoryRequest;
import ru.goltsov.education.web.model.response.CategoryResponse;
import ru.goltsov.education.web.model.response.NewsCategoryListResponse;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody @Valid CategoryRequest request) {
        return categoryMapper.categoryToResponse(
                categoryService.save(
                        categoryMapper.requestToNewsCategory(request)
                )
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public NewsCategoryListResponse findAll(Pageable pageable) {
        return categoryMapper.newsCategoryListToNewsCategoryListResponse(categoryService.findAll(pageable).stream().toList());
    }
}
