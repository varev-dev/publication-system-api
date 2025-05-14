package pl.edu.pg.publication_system.article.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.account.repository.AccountRepository;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.repository.ArticleRepository;
import pl.edu.pg.publication_system.security.access.AccessService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Article> getArticle(UUID id) {
        return articleRepository.findById(id);
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
        return articleRepository.save(article);
    }
}
