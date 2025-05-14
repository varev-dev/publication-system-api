package pl.edu.pg.publication_system.article.service;

import org.springframework.stereotype.Service;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.repository.ArticleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Optional<Article> getArticle(UUID id) {
        return articleRepository.findById(id);
    }
}
