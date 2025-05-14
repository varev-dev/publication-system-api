package pl.edu.pg.publication_system.article.service;

import org.springframework.stereotype.Service;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.account.repository.AccountRepository;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {

    private final AccountRepository accountRepository;
    private final ArticleRepository articleRepository;

    public ArticleService(AccountRepository accountRepository, ArticleRepository articleRepository) {
        this.accountRepository = accountRepository;
        this.articleRepository = articleRepository;
    }

    public Optional<Article> getArticle(UUID id) {
        return articleRepository.findById(id);
    }

    public List<Article> getArticlesWithAuthor(String author) {
        Optional<Account> authorAccount = accountRepository.findByUsername(author);

        if (authorAccount.isEmpty())
            return List.of();

        return articleRepository.findAllByAuthor(authorAccount.get());
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }
}
