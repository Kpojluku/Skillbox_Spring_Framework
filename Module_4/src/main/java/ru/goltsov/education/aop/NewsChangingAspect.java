package ru.goltsov.education.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.goltsov.education.exception.AccessDeniedException;
import ru.goltsov.education.service.NewsService;
import ru.goltsov.education.web.model.request.NewsDeleteRequest;
import ru.goltsov.education.web.model.request.UpsertNewsRequest;

@Aspect
@Component
public class NewsChangingAspect {

    @Autowired
    private NewsService newsService;

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.delete(..)) && args(newsDeleteRequest)")
    public void checkUserPermissionForDeletingNews(NewsDeleteRequest newsDeleteRequest) {
        if (!newsService.validateDeleting(newsDeleteRequest)) {
            throw new AccessDeniedException("User is not allowed to delete this news");
        }
    }

    @Before("execution(* ru.goltsov.education.web.controller.NewsController.updateNews(..)) && args(newsId, request)")
    public void checkUserPermissionForChangingNews(Long newsId, UpsertNewsRequest request) {
        if (!newsService.validateChanging(request, newsId)) {
            throw new AccessDeniedException("User is not allowed to change this news");
        }
    }
}