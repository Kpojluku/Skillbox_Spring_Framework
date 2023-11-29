package ru.goltsov.education.mapper;

import org.mapstruct.Mapper;
import ru.goltsov.education.model.NewsCategory;
import ru.goltsov.education.model.User;
import ru.goltsov.education.web.model.request.CategoryRequest;
import ru.goltsov.education.web.model.response.CategoryResponse;
import ru.goltsov.education.web.model.response.NewsCategoryListResponse;
import ru.goltsov.education.web.model.response.UserListResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    NewsCategory requestToNewsCategory(CategoryRequest request);

    CategoryResponse categoryToResponse(NewsCategory category);


    default NewsCategoryListResponse newsCategoryListToNewsCategoryListResponse(List<NewsCategory> categories) {
        NewsCategoryListResponse response = new NewsCategoryListResponse();
        response.setCategoryResponseList(categories.stream().map(this::categoryToResponse).toList());
        return response;
    }


}
