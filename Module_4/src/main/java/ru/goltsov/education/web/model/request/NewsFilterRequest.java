package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewsFilterRequest {

    @NotBlank(message = "Категория должна быть заполнено!")
    private String category;
    private String user_name;

}
