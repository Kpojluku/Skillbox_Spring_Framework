package ru.goltsov.education.web.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertCommentRequest {

    private long comment_id;
    @NotBlank(message = "Комментарий не должен быть пустым!")
    private String comment;

}
