package ru.goltsov.education.web.model.response;

import lombok.Data;

@Data
public class CommentResponse {

    private long id;    // комментария
    private long user_id;   // владельца комментария
    private long news_id;   // новости
    private String comment;


}
