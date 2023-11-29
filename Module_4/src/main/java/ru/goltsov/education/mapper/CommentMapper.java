package ru.goltsov.education.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import ru.goltsov.education.model.Comment;
import ru.goltsov.education.web.model.request.CommentDeleteRequest;
import ru.goltsov.education.web.model.request.CommentRequest;
import ru.goltsov.education.web.model.request.UpsertCommentRequest;
import ru.goltsov.education.web.model.response.CommentResponse;

import static org.mapstruct.ReportingPolicy.IGNORE;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface CommentMapper {

    Comment commentRequestToComment(CommentRequest commentRequest);

    CommentResponse commentToCommentResponse(Comment comment);

    Comment commentDeleteRequestToComment(CommentDeleteRequest commentDeleteRequest);

    Comment upsertCommentRequestToComment(UpsertCommentRequest request);
}
