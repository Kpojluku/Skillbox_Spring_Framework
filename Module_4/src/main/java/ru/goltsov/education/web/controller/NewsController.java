package ru.goltsov.education.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.goltsov.education.mapper.CommentMapper;
import ru.goltsov.education.mapper.NewsMapper;
import ru.goltsov.education.service.CategoryService;
import ru.goltsov.education.service.CommentService;
import ru.goltsov.education.service.NewsService;
import ru.goltsov.education.service.UserService;
import ru.goltsov.education.web.model.request.*;
import ru.goltsov.education.web.model.response.CommentResponse;
import ru.goltsov.education.web.model.response.NewsListResponse;
import ru.goltsov.education.web.model.response.NewsResponse;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsMapper newsMapper;
    private final CommentMapper commentMapper;
    private final NewsService newsService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @Operation(
            summary = "Save new news.",
            description = "Save new news. Return id, user_id, category, title, content, commentResponseList.",
            tags = {"news"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Not correct news request",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> createNews(@RequestBody @Valid NewsRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                newsMapper.newsToResponse(
                        newsService.save(
                                newsMapper.requestToNews(request, userDetails.getUsername(), userService, categoryService)
                        ),
                        commentService,
                        commentMapper
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> updateNews(@PathVariable("id") Long newsId, @RequestBody @Valid UpsertNewsRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
            return ResponseEntity.ok(
                newsMapper.newsToResponse(newsService.update(newsMapper.requestToNews(request, newsId)), commentService, commentMapper)
        );
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsListResponse> findAll(Pageable pageable) {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findAll(pageable).stream().toList()));
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NewsListResponse> findAllWithFilter(@RequestBody @Valid NewsFilterRequest filterRequest) {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(newsService.findAllWithFilter(filterRequest).stream().toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponse> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(newsMapper.newsToResponse(newsService.findById(id), commentService, commentMapper));
    }


    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@RequestBody @Valid NewsDeleteRequest newsDeleteRequest,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        newsService.delete(newsMapper.newsForDeleteRequestToNews(newsDeleteRequest));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/addComment")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> addComment(@RequestBody @Valid CommentRequest request,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                commentMapper.commentToCommentResponse(
                        commentService.save(
                                commentMapper.commentRequestToComment(request, userDetails.getUsername())
                        )
                )
        );
    }

    @DeleteMapping("/comments")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> deleteComment(@RequestBody @Valid CommentDeleteRequest commentDeleteRequest,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        commentService.delete(commentMapper.commentDeleteRequestToComment(commentDeleteRequest));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/comments")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody @Valid UpsertCommentRequest request,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                commentMapper.commentToCommentResponse(
                        commentService.update(
                                commentMapper.upsertCommentRequestToComment(request)
                        )
                )
        );
    }
}
