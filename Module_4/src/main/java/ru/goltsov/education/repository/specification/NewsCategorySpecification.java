package ru.goltsov.education.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.goltsov.education.model.NewsCategory;

public interface NewsCategorySpecification {

    static Specification<NewsCategory> byCategory(String category) {
        return ((root, query, criteriaBuilder) -> {
            if (category == null) return null;
            return criteriaBuilder.equal(root.get("category"), category);
        });
    }

}
