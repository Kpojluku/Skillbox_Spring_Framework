package ru.goltsov.education.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.goltsov.education.model.News;
import ru.goltsov.education.web.model.request.NewsFilterRequest;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilterRequest filter) {
        return Specification.where(
                byUserName(filter.getUser_name()))
                .and(byCategory(filter.getCategory()));
    }

    static Specification<News> byCategory(String category) {
        return ((root, query, criteriaBuilder) -> {
            if (category == null) return null;
            return criteriaBuilder.equal(root.get("newsCategory").get("category"), category);
        });
    }

    static Specification<News> byUserName(String user_name) {
        return ((root, query, criteriaBuilder) -> {
            if (user_name == null) return null;
            return criteriaBuilder.equal(root.get("user").get("name"), user_name);
        });
    }


}
