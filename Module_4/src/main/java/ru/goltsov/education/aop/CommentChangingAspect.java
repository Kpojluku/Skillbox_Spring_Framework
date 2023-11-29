package ru.goltsov.education.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.goltsov.education.exception.AccessDeniedException;
import ru.goltsov.education.service.CommentService;
import ru.goltsov.education.web.model.request.CommentDeleteRequest;
import ru.goltsov.education.web.model.request.UpsertCommentRequest;

@Aspect
@Component
public class CommentChangingAspect {

    @Autowired
    private CommentService commentService;

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.deleteComment(..)) && args(commentDeleteRequest)")
    public void checkUserPermissionForDeletingNews(CommentDeleteRequest commentDeleteRequest) {
        if (!commentService.validateDeleting(commentDeleteRequest)) {
            throw new AccessDeniedException("User is not allowed to delete this comment");
        }
    }

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.updateComment(..)) && args(request)")
    public void checkUserPermissionForChangingNews(UpsertCommentRequest request) {
        if (!commentService.validateChanging(request)) {
            throw new AccessDeniedException("User is not allowed to change this comment");
        }
    }
}
