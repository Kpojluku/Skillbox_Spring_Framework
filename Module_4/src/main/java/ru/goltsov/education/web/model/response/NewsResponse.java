package ru.goltsov.education.web.model.response;

import lombok.Data;

import java.util.List;

@Data
public class NewsResponse {

    private long id;
    private long user_id;
    private String category;
    private String title;
    private String content;
    private List<CommentResponse> commentResponseList;


}
