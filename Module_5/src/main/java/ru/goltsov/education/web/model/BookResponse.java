package ru.goltsov.education.web.model;

import lombok.Data;
import ru.goltsov.education.model.Category;

@Data
public class BookResponse {
    private Long id;
    private String name;
    private String author;
    private Category category;
}
