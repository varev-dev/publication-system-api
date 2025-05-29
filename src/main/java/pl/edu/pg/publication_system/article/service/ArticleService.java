package pl.edu.pg.publication_system.article.service;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.account.repository.AccountRepository;
import pl.edu.pg.publication_system.article.dto.ArticleSummaryDTO;
import pl.edu.pg.publication_system.article.mapper.ArticleMapper;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.repository.ArticleRepository;
import pl.edu.pg.publication_system.security.access.AccessService;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final AccountRepository accountRepository;
    private final ArticleRepository articleRepository;
    private final AccessService accessService;

    public ArticleService(AccountRepository accountRepository, ArticleRepository articleRepository, AccessService accessService) {
        this.accountRepository = accountRepository;
        this.articleRepository = articleRepository;
        this.accessService = accessService;
    }

    public Optional<Article> getArticle(long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        Hibernate.initialize(article.getComments());

        return Optional.of(article);
    }

    public List<ArticleSummaryDTO> findArticles(String author, Integer requiredAge, Pageable pageable) {
        Specification<Article> spec = Specification.where(null);

        if (author != null && !author.isEmpty())
            spec.and((root, ignored, cb) -> cb.equal(root.get("author"), author));

        if (requiredAge != null)
            spec.and((root, ignored, cb) -> cb.equal(root.get("requiredAge"), requiredAge));

        Page<Article> page = articleRepository.findAll(spec, pageable);
        return page.stream().map(ArticleMapper::toArticleSummaryDTO).toList();
    }

    public List<Article> getArticlesWithAuthor(String author) {
        Optional<Account> authorAccount = accountRepository.findByUsername(author);

        if (authorAccount.isEmpty())
            return List.of();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account user = (Account) authentication.getPrincipal();

        return articleRepository.findAllByAuthor(authorAccount.get()).stream()
                .filter(article -> accessService.canViewArticle(article, user)).toList();
    }

    public Article createArticle(Article article) {
        try {
            return articleRepository.save(article);
        } catch (Exception e) {
            return null;
        }
    }
}
