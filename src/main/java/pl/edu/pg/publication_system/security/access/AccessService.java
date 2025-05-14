package pl.edu.pg.publication_system.security.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.repository.ArticleRepository;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Component("accessService")
public class AccessService {

    private final ArticleRepository articleRepository;

    public AccessService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public boolean canViewArticle(UUID articleId, Account user) {
        Optional<Article> articleOpt = articleRepository.findById(articleId);

        if (articleOpt.isEmpty())
            return false;

        Article article = articleOpt.get();

        if (article.hasRequiredAge() && user.getUserAge() < article.getRequiredAge())
            return false;

        if (article.hasRequiredAccountAge() && user.getAccountAgeInHours() < article.getRequiredAccountAge().get(ChronoUnit.HOURS))
            return false;

        return true;
    }

}
