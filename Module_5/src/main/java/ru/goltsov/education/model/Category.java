package ru.goltsov.education.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(name = "categories")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NonNull
    private String categoryName;


}
