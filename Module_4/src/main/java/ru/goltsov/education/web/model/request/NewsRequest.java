package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewsRequest {

    private long category_id;
    @NotBlank(message = "Заголовок должен быть заполнен!")
    private String title;
    @NotBlank(message = "Содержание должно быть заполнено!")
    private String content;

}
