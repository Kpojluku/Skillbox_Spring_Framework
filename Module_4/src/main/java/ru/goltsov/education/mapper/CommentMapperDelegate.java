package ru.goltsov.education.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import ru.goltsov.education.model.Comment;
import ru.goltsov.education.service.NewsService;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.web.model.request.CommentDeleteRequest;
import ru.goltsov.education.web.model.request.CommentRequest;
import ru.goltsov.education.web.model.request.UpsertCommentRequest;
import ru.goltsov.education.web.model.response.CommentResponse;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @Override
    public Comment commentRequestToComment(CommentRequest commentRequest) {
        return Comment.builder()
                .user(userService.findById(commentRequest.getUser_id()))
                .news(newsService.findById(commentRequest.getNews_id()))
                .comment(commentRequest.getComment())
                .build();
    }

    @Override
    public CommentResponse commentToCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setId(comment.getId());
        commentResponse.setComment(comment.getComment());
        commentResponse.setNews_id(comment.getNews().getId());
        commentResponse.setUser_id(comment.getUser().getId());

        return commentResponse;
    }

    @Override
    public Comment commentDeleteRequestToComment(CommentDeleteRequest commentDeleteRequest) {
        return Comment.builder()
                .id(commentDeleteRequest.getComment_id())
                .build();
    }

    @Override
    public Comment upsertCommentRequestToComment(UpsertCommentRequest request){
        return Comment.builder()
                .comment(request.getComment())
                .id(request.getComment_id())
                .comment(request.getComment())
                .build();
    }

}
