package ru.goltsov.education.web.model.request;

import lombok.Data;

@Data
public class CommentDeleteRequest {

    private long token;
    private long comment_id;

}
