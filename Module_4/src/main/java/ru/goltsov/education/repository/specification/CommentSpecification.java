package ru.goltsov.education.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.goltsov.education.model.Comment;

public interface CommentSpecification {

    static Specification<Comment> byNewsId(long newsId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("news").get("id"), newsId));
    }

}
