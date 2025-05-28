package pl.edu.pg.publication_system.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.comment.dto.CommentCreationRequest;
import pl.edu.pg.publication_system.comment.model.Comment;
import pl.edu.pg.publication_system.comment.service.CommentService;

import java.util.List;

@RequestMapping(path = "/articles/{articleId}/comments")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long articleId) {
        List<Comment> comments = commentService.findCommentsByArticleId(articleId);

        if (comments == null)
            return ResponseEntity.ok(List.of());

        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<?> addComment(
            @PathVariable("articleId") Long articleId,
            @RequestBody CommentCreationRequest content,
            Authentication authentication) {

        Account author = (Account) authentication.getPrincipal();
        Comment comment = commentService.save(author, articleId, content.content());

        if (comment == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long articleId,
            @PathVariable Long id) {
        commentService.removeComment(id);
        return ResponseEntity.ok().build();
    }
}
