package ru.goltsov.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.goltsov.education.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    List<Comment> findAllByNewsId(Long newsId);

    Boolean existsByIdAndUserId(Long commentId, Long userId);

}
