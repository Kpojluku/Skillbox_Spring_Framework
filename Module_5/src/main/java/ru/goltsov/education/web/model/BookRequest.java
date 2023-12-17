package ru.goltsov.education.web.model;

import lombok.Data;

@Data
public class BookRequest {

    private String name;
    private String author;
    private String categoryName;

}
