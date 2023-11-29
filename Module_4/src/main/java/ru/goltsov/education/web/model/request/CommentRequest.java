package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    private long news_id; // новости
    private long user_id;// владельца комментария
    @NotBlank(message = "Комментарий не должен быть пустым!")
    private String comment;

}
