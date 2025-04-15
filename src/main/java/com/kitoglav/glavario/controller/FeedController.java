package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.jpa.models.Comment;
import com.kitoglav.glavario.jpa.models.Post;
import com.kitoglav.glavario.rest.ApiResponseException;
import com.kitoglav.glavario.rest.dtos.CommentRequestDto;
import com.kitoglav.glavario.services.FeedService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
@RequestMapping("/api/feed")

//TODO: replace all responses from JPA entities to DTOs
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/post/{id}")
    private ResponseEntity<Post> getPost(@PathVariable long id) {
        return ResponseEntity.ofNullable(feedService.getPost(id).orElse(null));
    }

    @GetMapping("/post/{id}/comments")
    private ResponseEntity<Page<Comment>> getComments(@PathVariable long id, @PageableDefault(sort = "postTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(feedService.getCommentsFor(id, pageable));
    }

    @PostMapping("/post")
    private ResponseEntity<Post> addPost(@RequestBody String content) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.addPost(content));
    }

    @PostMapping("/comment")
    private ResponseEntity<Comment> addComment(@Valid @RequestBody CommentRequestDto request) {
        return feedService.addCommentTo(request.getId(), request.getContent()).map(comment -> ResponseEntity.status(HttpStatus.CREATED).body(comment)).orElseThrow(() -> new ApiResponseException(HttpStatus.NOT_FOUND, "Пост с ID: {%d} не существует".formatted(request.getId())));
    }

    @ExceptionHandler(value = ApiResponseException.class)
    private ApiResponseException handleApiExceptions(ApiResponseException e) {
        return e;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
