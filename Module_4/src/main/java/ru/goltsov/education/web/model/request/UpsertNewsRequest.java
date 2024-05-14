package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertNewsRequest {

    private String title;
    @NotBlank(message = "Содержание должно быть заполнено!")
    private String content;
}
