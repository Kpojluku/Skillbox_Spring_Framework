package ru.goltsov.education.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.goltsov.education.exception.AccessDeniedException;
import ru.goltsov.education.service.CommentService;
import ru.goltsov.education.web.model.request.CommentDeleteRequest;
import ru.goltsov.education.web.model.request.UpsertCommentRequest;

import static ru.goltsov.education.utils.SecurityUtils.isUserRoleOnly;

@Aspect
@Component
public class CommentChangingAspect {

    @Autowired
    private CommentService commentService;

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.deleteComment(..)) && args(commentDeleteRequest, userDetails)")
    public void checkUserPermissionForDeletingNews(CommentDeleteRequest commentDeleteRequest, UserDetails userDetails) {
        if (isUserRoleOnly(userDetails) && !commentService.validateChanging(commentDeleteRequest.getComment_id(), userDetails.getUsername())) {
            throw new AccessDeniedException("Пользователь, с ролью ROLE_USER, может удалить только свой комментарий");
        }
    }

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.updateComment(..)) && args(request, userDetails)")
    public void checkUserPermissionForChangingNews(UpsertCommentRequest request, UserDetails userDetails) {
        if (isUserRoleOnly(userDetails) && !commentService.validateChanging(request.getComment_id(), userDetails.getUsername())) {
            throw new AccessDeniedException("Пользователь, с ролью ROLE_USER, может редактировать только свой комментарий");
        }
    }
}
