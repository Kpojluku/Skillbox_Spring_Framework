package ru.goltsov.education.web.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsCategoryListResponse {

    private List<CategoryResponse> categoryResponseList = new ArrayList<>();

}
