package ru.goltsov.education.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.goltsov.education.exception.EntityNotFoundException;
import ru.goltsov.education.model.Comment;
import ru.goltsov.education.model.News;
import ru.goltsov.education.repository.CommentRepository;
import ru.goltsov.education.repository.specification.CommentSpecification;
import ru.goltsov.education.utils.BeenUtils;
import ru.goltsov.education.web.model.request.CommentDeleteRequest;
import ru.goltsov.education.web.model.request.UpsertCommentRequest;
import ru.goltsov.education.web.model.response.CommentResponse;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }


    public List<Comment> findByNewsId(long newsId) {
        return commentRepository.findAll(CommentSpecification.byNewsId(newsId));

        // или так
//        return commentRepository.findAllByNewsId(newsId);
    }

    public void delete(Comment commentForDelete) {
        commentRepository.delete(commentForDelete);
    }

    public boolean validateDeleting(CommentDeleteRequest commentDeleteRequest) {
        return validate(commentDeleteRequest.getComment_id(), commentDeleteRequest.getToken());

    }

    public boolean validateChanging(UpsertCommentRequest upsertCommentRequest) {
        return validate(upsertCommentRequest.getComment_id(), upsertCommentRequest.getToken());

    }

    private boolean validate(long commentId, long userId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            return commentOptional.get().getUser().getId().equals(userId);
        } else {
            throw new EntityNotFoundException(MessageFormat.format("Комментарий с ID {0} не найден!", commentId));
        }

        //или так
//        return commentRepository.existsByIdAndUserId(commentId, userId);

    }

    public Comment update(Comment comment) {
        Comment commentForUpdate = commentRepository.findById(comment.getId()).stream().findAny().orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Комментарий с ID {0} не найден!", comment.getId())));

        BeenUtils.copyNonNullProperties(comment, commentForUpdate);

        return save(commentForUpdate);

    }
}
