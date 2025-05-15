package pl.edu.pg.publication_system.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.publication_system.article.dto.ArticleCreationRequest;
import pl.edu.pg.publication_system.article.dto.ArticleDetailsDTO;
import pl.edu.pg.publication_system.article.dto.ArticleSummaryDTO;
import pl.edu.pg.publication_system.article.mapper.ArticleMapper;
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
    public ResponseEntity<ArticleDetailsDTO> getArticle(@PathVariable String id) {
        Optional<Article> articleOpt = articleService.getArticle(UUID.fromString(id));

        return articleOpt.map(ArticleMapper::toArticleDetailsDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ArticleSummaryDTO> getArticlesFromAuthor(@RequestParam(name = "author") String author) {
        return articleService.getArticlesWithAuthor(author).stream().map(ArticleMapper::toArticleSummaryDTO).toList();
    }

    @PostMapping
    public ResponseEntity<ArticleDetailsDTO> createArticle(@RequestBody ArticleCreationRequest articleRequest) {
        Article article = ArticleMapper.fromArticleCreationRequest(articleRequest);
        article = articleService.createArticle(article);

        if (article == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(ArticleMapper.toArticleDetailsDTO(article));
    }

}
