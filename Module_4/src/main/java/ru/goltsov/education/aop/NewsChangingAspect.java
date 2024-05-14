package ru.goltsov.education.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.goltsov.education.exception.AccessDeniedException;
import ru.goltsov.education.service.NewsService;
import ru.goltsov.education.web.model.request.NewsDeleteRequest;
import ru.goltsov.education.web.model.request.UpsertNewsRequest;

import java.util.List;

import static ru.goltsov.education.utils.SecurityUtils.isUserRoleOnly;

@Aspect
@Component
public class NewsChangingAspect {

    @Autowired
    private NewsService newsService;

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.delete(..)) && args(newsDeleteRequest, userDetails)")
    public void checkUserPermissionForDeletingNews(NewsDeleteRequest newsDeleteRequest, UserDetails userDetails) {
        if (isUserRoleOnly(userDetails) && !newsService.validateChanging(userDetails.getUsername(), newsDeleteRequest.getNews_id())) {
            throw new AccessDeniedException("Пользователь, с ролью ROLE_USER, может удалить только свою новость");
        }
    }

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.updateNews(..)) && args(newsId, request, userDetails)")
    public void checkUserPermissionForChangingNews(Long newsId, UpsertNewsRequest request, UserDetails userDetails) {
        if (isUserRoleOnly(userDetails) && !newsService.validateChanging(userDetails.getUsername(), newsId)) {
            throw new AccessDeniedException("Пользователь, с ролью ROLE_USER, может редактировать только свою новость");
        }
    }
}