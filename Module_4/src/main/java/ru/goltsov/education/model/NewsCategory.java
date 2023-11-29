package ru.goltsov.education.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "news_categories")
public class NewsCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String category;

}
