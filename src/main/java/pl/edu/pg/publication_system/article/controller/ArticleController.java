package pl.edu.pg.publication_system.article.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.dto.ArticleCreationRequest;
import pl.edu.pg.publication_system.article.dto.ArticleDetailsDTO;
import pl.edu.pg.publication_system.article.dto.ArticleSummaryDTO;
import pl.edu.pg.publication_system.article.mapper.ArticleMapper;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.service.ArticleService;

import java.util.List;

@RequestMapping(path = "/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PreAuthorize("@accessService.canViewArticle(#id, authentication.principal) || hasAnyRole('EDITOR', 'ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<ArticleDetailsDTO> getArticle(@PathVariable Long id) {
        Article article = articleService.getArticle(id).orElseThrow();

        return ResponseEntity.ok(ArticleMapper.toArticleDetailsDTO(article));
    }

    @GetMapping
    public List<ArticleSummaryDTO> getArticles(
            @RequestParam(required = false) String author,
            @RequestParam(name = "required-age", required = false) Integer requiredAge,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {

        Pageable pageable = createPageableFromParams(page, size, sort);
        return articleService.findArticles(author, requiredAge, pageable);
    }

    @PostMapping
    public ResponseEntity<ArticleDetailsDTO> createArticle(
            @RequestBody ArticleCreationRequest articleRequest,
            Authentication authentication) {
        Account editor = (Account) authentication.getPrincipal();
        Article article = ArticleMapper.fromArticleCreationRequest(articleRequest, editor);
        article = articleService.createArticle(article);

        if (article == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(ArticleMapper.toArticleDetailsDTO(article));
    }

    private Pageable createPageableFromParams(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }

}
