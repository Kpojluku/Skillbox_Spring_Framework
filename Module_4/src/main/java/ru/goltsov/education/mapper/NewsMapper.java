package ru.goltsov.education.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.goltsov.education.model.News;
import ru.goltsov.education.model.NewsCategory;
import ru.goltsov.education.model.User;
import ru.goltsov.education.service.CategoryService;
import ru.goltsov.education.service.CommentService;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.web.model.request.NewsDeleteRequest;
import ru.goltsov.education.web.model.request.NewsRequest;
import ru.goltsov.education.web.model.request.UpsertNewsRequest;
import ru.goltsov.education.web.model.response.NewsListResponse;
import ru.goltsov.education.web.model.response.NewsResponse;
import ru.goltsov.education.web.model.response.NewsResponseWithoutComment;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(source = "newsId", target = "id")
    News requestToNews(UpsertNewsRequest request, long newsId);

    default News requestToNews(NewsRequest request, String userName, UserService userService, CategoryService categoryService) {
        User user = userService.findByUsername(userName);
        NewsCategory newsCategory = categoryService.findById(request.getCategory_id());

        News news = new News();
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setUser(user);
        news.setNewsCategory(newsCategory);
        return news;
    }

    default NewsResponse newsToResponse(News news, CommentService commentService, CommentMapper commentMapper) {
        NewsResponse newsResponse = new NewsResponse();

        newsResponse.setTitle(news.getTitle());
        newsResponse.setContent(news.getContent());
        newsResponse.setCategory(news.getNewsCategory().getCategory());
        newsResponse.setId(news.getId());
        newsResponse.setUser_id(news.getUser().getId());
        newsResponse.setCommentResponseList(
                commentService.findByNewsId(news.getId()).stream().map(commentMapper::commentToCommentResponse).collect(Collectors.toList())
        );

        return newsResponse;
    }

    default NewsResponseWithoutComment newsWithoutCommentToResponse(News news) {
        NewsResponseWithoutComment newsResponseWithoutComment = new NewsResponseWithoutComment();

        newsResponseWithoutComment.setId(news.getId());
        newsResponseWithoutComment.setUser_id(news.getUser().getId());
        newsResponseWithoutComment.setCategory(news.getNewsCategory().getCategory());
        newsResponseWithoutComment.setTitle(news.getTitle());
        newsResponseWithoutComment.setContent(news.getContent());
        newsResponseWithoutComment.setCommentCount(news.getComments().size());

        return newsResponseWithoutComment;
    }

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse newsListResponse = new NewsListResponse();
        List<NewsResponseWithoutComment> newsResponseWithoutComments = newsList.stream().map(this::newsWithoutCommentToResponse).toList();
        newsListResponse.setNews(newsResponseWithoutComments);
        return newsListResponse;
    }

    @Mapping(source = "news_id", target = "id")
    News newsForDeleteRequestToNews(NewsDeleteRequest newsDeleteRequest);


}
