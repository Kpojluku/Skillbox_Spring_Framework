package ru.goltsov.education.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsListResponse {

    private List<NewsResponseWithoutComment> news = new ArrayList<>();

}
