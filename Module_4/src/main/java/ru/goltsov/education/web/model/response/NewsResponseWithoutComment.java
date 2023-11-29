package ru.goltsov.education.web.model.response;

import lombok.Data;

@Data
public class NewsResponseWithoutComment {

    private long id;
    private long user_id;
    private String category;
    private String title;
    private String content;
    private int commentCount;


}
