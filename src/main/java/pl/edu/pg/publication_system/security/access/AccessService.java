package pl.edu.pg.publication_system.security.access;

import org.springframework.stereotype.Component;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.repository.ArticleRepository;

import java.util.Optional;

@Component("accessService")
public class AccessService {

    private final ArticleRepository articleRepository;

    public AccessService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public boolean canViewArticle(Long articleId, Account user) {
        Optional<Article> articleOpt = articleRepository.findById(articleId);

        return articleOpt.filter(article -> canViewArticle(article, user)).isPresent();
    }

    public boolean canViewArticle(Article article, Account user) {
        if (article.hasRequiredAge() && user.getUserAge() < article.getRequiredAge())
            return false;

        if (article.hasRequiredAccountAge() && user.getAccountAgeInHours() < article.getRequiredAccountAge().toHours())
            return false;

        return true;
    }

}
