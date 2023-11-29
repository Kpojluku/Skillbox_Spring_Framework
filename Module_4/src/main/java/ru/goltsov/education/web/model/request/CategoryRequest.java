package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Категория должна быть заполнено!")
    @Size(min = 3, max = 30, message = "Длина должна быть между {min} и {max} символами")
    private String category;

}
