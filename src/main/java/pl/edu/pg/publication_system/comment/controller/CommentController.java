package pl.edu.pg.publication_system.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.publication_system.comment.model.Comment;
import pl.edu.pg.publication_system.comment.service.CommentService;

@RequestMapping(path = "/article/{articleId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> addComment(
            @PathVariable("articleId") Long articleId,
            @RequestBody Comment comment) {
        return ResponseEntity.badRequest().build();
    }

}
