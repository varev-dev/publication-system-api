package pl.edu.pg.publication_system.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.service.ArticleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping(path = "/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PreAuthorize("@accessService.canViewArticle(id, authentication.principal)")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getArticle(@PathVariable UUID id) {
        Optional<Article> articleOpt = articleService.getArticle(id);

        if (articleOpt.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(articleOpt.get());
    }

    @GetMapping
    public List<Article> getArticlesFromAuthor(@RequestParam(name = "author") String author) {

    }

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody Article article) {
        article = articleService.createArticle(article);

        if (article == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(article);
    }

}
