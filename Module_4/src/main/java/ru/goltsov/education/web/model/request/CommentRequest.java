package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    private long news_id; // новости
    @NotBlank(message = "Комментарий не должен быть пустым!")
    private String comment;

}
